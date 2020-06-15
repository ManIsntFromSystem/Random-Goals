package com.quantumman.randomgoals.data

import android.content.*
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.net.Uri
import android.util.Log
import com.quantumman.randomgoals.data.GoalsContract.AUTHORITY
import com.quantumman.randomgoals.data.GoalsContract.PATH_GOAL
import com.quantumman.randomgoals.data.GoalsContract.PATH_LIST
import com.quantumman.randomgoals.data.GoalsContract.ItemGoalEntry.TABLE_NAME_GOAL
import com.quantumman.randomgoals.data.GoalsContract.ItemGoalEntry.ID_GOAL
import com.quantumman.randomgoals.data.GoalsContract.ItemGoalEntry.COLUMN_NAME_GOAL
import com.quantumman.randomgoals.data.GoalsContract.ItemGoalEntry.COLUMN_ID_LIST
import com.quantumman.randomgoals.data.GoalsContract.ItemGoalEntry.CONTENT_MULTIPLE_GOALS
import com.quantumman.randomgoals.data.GoalsContract.ItemGoalEntry.CONTENT_SINGLE_GOAL
import com.quantumman.randomgoals.data.GoalsContract.GoalsListEntry.TABLE_NAME_LIST
import com.quantumman.randomgoals.data.GoalsContract.GoalsListEntry.ID_LIST
import com.quantumman.randomgoals.data.GoalsContract.GoalsListEntry.COLUMN_NAME_LIST
import com.quantumman.randomgoals.data.GoalsContract.GoalsListEntry.COLUMN_ICON_GOAL
import com.quantumman.randomgoals.data.GoalsContract.GoalsListEntry.CONTENT_MULTIPLE_LISTS
import com.quantumman.randomgoals.data.GoalsContract.GoalsListEntry.CONTENT_SINGLE_LIST

class GoalsContentProvider() : ContentProvider() {
    private var goalsDBHelper: GoalDBOpenHelper? = null

    companion object {
        private const val MATCHER_ALL_GOAL = 110
        private const val MATCHER_GOAL = 120
        private const val MATCHER_ALL_LIST = 210
        private const val MATCHER_LIST = 220

        private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
            addURI(AUTHORITY, PATH_GOAL, MATCHER_ALL_GOAL)
            addURI(AUTHORITY,"${PATH_GOAL}/#", MATCHER_GOAL)
            addURI(AUTHORITY, PATH_LIST, MATCHER_ALL_LIST)
            addURI(AUTHORITY,"${PATH_GOAL}/#", MATCHER_LIST)
        }
    }

    override fun onCreate(): Boolean {
        goalsDBHelper = GoalDBOpenHelper(context)
        return true
    }

    override fun query(uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?): Cursor? {
        val db: SQLiteDatabase = goalsDBHelper!!.readableDatabase
        val cursor: Cursor = when (uriMatcher.match(uri)) {
            MATCHER_ALL_GOAL -> db.query(
                TABLE_NAME_GOAL, projection, selection, selectionArgs,
                null, null, sortOrder
            )
            MATCHER_GOAL -> {
                val mSelection = "$ID_GOAL=?"
                val mSelectionArgs = arrayOf(ContentUris.parseId(uri).toString())
                db.query(
                    TABLE_NAME_GOAL, projection, mSelection, mSelectionArgs,
                    null, null, null, sortOrder
                )
            }
            MATCHER_ALL_LIST -> db.query(
                TABLE_NAME_LIST, projection, selection, selectionArgs,
                null, null, sortOrder
            )
            MATCHER_LIST -> {
                val mSelection = "$ID_LIST=?"
                val mSelectionArgs = arrayOf(ContentUris.parseId(uri).toString())
                db.query(
                    TABLE_NAME_LIST, projection, mSelection, mSelectionArgs,
                    null, null, null, sortOrder
                )
            }
            else -> throw IllegalArgumentException("Can't query incorrect URI: $uri")
        }
        cursor.setNotificationUri(context!!.contentResolver,uri)
        return cursor
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val nameGoal = values!!.getAsString(COLUMN_NAME_GOAL)
            ?: throw IllegalArgumentException("Input goal name")
        val nameListGoals = values.getAsString(COLUMN_NAME_LIST)
            ?: throw IllegalArgumentException("Input list name")
        val db: SQLiteDatabase = goalsDBHelper!!.writableDatabase
        val resultUri = when (uriMatcher.match(uri)) {
            MATCHER_ALL_GOAL -> {
                val id = db.insert(TABLE_NAME_GOAL, null, values)
                if (id == -1L) {
                    Log.e("InsertMethod", "Insertion in the GOALS failed for $uri")
                    return null
                }
                ContentUris.withAppendedId(uri, id)
            }
            MATCHER_ALL_LIST -> {
                val id = db.insert(TABLE_NAME_LIST, null, values)
                if (id == -1L) {
                    Log.e("InsertMethod", "Insertion in the LISTS failed for $uri")
                    return null
                }
                ContentUris.withAppendedId(uri, id)
            }
            else -> throw IllegalArgumentException("Insertion of data in the table failed for $uri")
        }
        context!!.contentResolver.notifyChange(uri, null)
        return resultUri
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        val db: SQLiteDatabase = goalsDBHelper!!.writableDatabase
        val rowsDeleted = when (uriMatcher.match(uri)) {
            MATCHER_ALL_GOAL -> db.delete(TABLE_NAME_GOAL, selection, selectionArgs)
            MATCHER_GOAL -> {
                    val mSelection = "$ID_GOAL=?"
                    val mSelectionArgs = arrayOf(ContentUris.parseId(uri).toString())
                    db.delete(TABLE_NAME_GOAL, mSelection, mSelectionArgs)
            }
            MATCHER_ALL_LIST -> db.delete(TABLE_NAME_LIST, selection, selectionArgs)
            MATCHER_LIST -> {
                val mSelection = "$ID_LIST=?"
                val mSelectionArgs = arrayOf(ContentUris.parseId(uri).toString())
                db.delete(TABLE_NAME_LIST, mSelection, mSelectionArgs)
            }
            else -> throw IllegalArgumentException("Can't delete incorrect URI: $uri")
        }
        if (rowsDeleted != 0) context!!.contentResolver.notifyChange(uri, null)
        return rowsDeleted
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?): Int {
        if (values!!.containsKey(COLUMN_NAME_GOAL)) values.getAsString(COLUMN_NAME_GOAL)
                ?: throw IllegalArgumentException("Input goal name")
        if (values.containsKey(COLUMN_NAME_LIST)) values.getAsString(COLUMN_NAME_LIST)
                ?: throw IllegalArgumentException("Input list name")
        val db: SQLiteDatabase = goalsDBHelper!!.writableDatabase
        val rowsUpdated: Int
        when (uriMatcher.match(uri)) {
            MATCHER_ALL_GOAL ->
                rowsUpdated = db.update(TABLE_NAME_GOAL, values, selection, selectionArgs)
            MATCHER_GOAL -> {
                val mSelection = "$ID_GOAL=?"
                val mSelectionArgs = arrayOf(ContentUris.parseId(uri).toString())
                rowsUpdated = db.update(TABLE_NAME_GOAL, values, mSelection, mSelectionArgs)
            }
            MATCHER_ALL_LIST ->
                rowsUpdated = db.update(TABLE_NAME_LIST, values, selection, selectionArgs)
            MATCHER_LIST -> {
                val mSelection = "$ID_LIST=?"
                val mSelectionArgs = arrayOf(ContentUris.parseId(uri).toString())
                rowsUpdated = db.update(TABLE_NAME_LIST, values, mSelection, mSelectionArgs)
            }
            else -> throw IllegalArgumentException("Can't update incorrect URI: $uri")
        }
        if (rowsUpdated != 0) context!!.contentResolver.notifyChange(uri, null)
        return rowsUpdated
    }

    override fun getType(uri: Uri): String? {
        return when (uriMatcher.match(uri)) {
            MATCHER_ALL_GOAL -> CONTENT_MULTIPLE_GOALS
            MATCHER_GOAL -> CONTENT_SINGLE_GOAL
            MATCHER_LIST -> CONTENT_MULTIPLE_LISTS
            MATCHER_ALL_LIST -> CONTENT_SINGLE_LIST
            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }
    }
}