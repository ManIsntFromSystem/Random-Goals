package com.quantumman.randomgoals.data

import android.annotation.SuppressLint
import android.content.*
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.net.Uri
import android.util.Log
import com.quantumman.randomgoals.data.GoalsContract.MemberEntry.COLUMN_NAME_ITEM_GOAL
import com.quantumman.randomgoals.data.GoalsContract.MemberEntry.COLUMN_NAME_LIST
import com.quantumman.randomgoals.data.GoalsContract.MemberEntry.CONTENT_MULTIPLE_ITEMS
import com.quantumman.randomgoals.data.GoalsContract.MemberEntry.CONTENT_SINGLE_ITEM
import com.quantumman.randomgoals.data.GoalsContract.MemberEntry.COLUMN_NAME_ICON_GOAL
import com.quantumman.randomgoals.data.GoalsContract.MemberEntry.TABLE_NAME
import com.quantumman.randomgoals.data.GoalsContract.MemberEntry._ID
import com.quantumman.randomgoals.model.Goal

class GoalsContentProvider() : ContentProvider() {
    private lateinit var goalsDBHelper: GoalDBOpenHelper
    companion object {
        private const val MATCHER_WHOLE_TABLE = 333
        private const val MATCHER_GOAL = 777
        private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
            addURI(
                GoalsContract.AUTHORITY,
                GoalsContract.PATH_PRODUCTS,
                MATCHER_WHOLE_TABLE
            )
            addURI(
                GoalsContract.AUTHORITY,
                "${GoalsContract.PATH_PRODUCTS}/#",
                MATCHER_GOAL
            )
        }
    }

    override fun onCreate(): Boolean {
        Log.d("MyLog", "OnCreate Content")
        goalsDBHelper = GoalDBOpenHelper(context)
        return true
    }

    override fun query(
        uri: Uri, projection: Array<String>?, mSelection: String?,
        mSelectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        var selection = mSelection
        var selectionArgs = mSelectionArgs
        val db: SQLiteDatabase = goalsDBHelper.readableDatabase
        val cursor: Cursor
        when (uriMatcher.match(uri)) {
            MATCHER_WHOLE_TABLE -> cursor = db.query(
                TABLE_NAME, projection, selection, selectionArgs,
                null, null, sortOrder
            )
            MATCHER_GOAL -> {
                selection = "$_ID=?"
                selectionArgs = arrayOf(ContentUris.parseId(uri).toString())
                cursor = db.query(
                    TABLE_NAME, projection, selection, selectionArgs,
                    null, null, null, sortOrder
                )
            }
            else -> throw IllegalArgumentException("Can't query incorrect URI: $uri")
        }
        cursor.setNotificationUri(
            context!!.contentResolver,
            uri
        ) //for defined which uri we need to use
        return cursor
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val nameGoal = values!!.getAsString(COLUMN_NAME_ITEM_GOAL)
            ?: throw IllegalArgumentException("Input goal name")
        val nameListGoals = values.getAsString(COLUMN_NAME_LIST)
            ?: throw IllegalArgumentException("Input list name")
        val db: SQLiteDatabase = goalsDBHelper.writableDatabase
        return when (uriMatcher.match(uri)) {
            MATCHER_WHOLE_TABLE -> {
                val id = db.insert(TABLE_NAME, null, values)
                if (id == -1L) {
                    Log.e("InsertMethod", "Insertion of data in the table failed for $uri")
                    return null
                }
                context!!.contentResolver.notifyChange(uri, null)
                ContentUris.withAppendedId(uri, id)
            }
            else -> throw IllegalArgumentException("Insertion of data in the table failed for $uri")
        }
    }

    override fun delete(uri: Uri, mSelection: String?, mSelectionArgs: Array<String>?): Int {
        var selection = mSelection
        var selectionArgs = mSelectionArgs
        val db: SQLiteDatabase = goalsDBHelper.writableDatabase
        val match = uriMatcher.match(uri)
        val rowsDeleted: Int
        when (match) {
            MATCHER_WHOLE_TABLE -> rowsDeleted =
                db.delete(TABLE_NAME, selection, selectionArgs)
            MATCHER_GOAL -> {
                selection = "$_ID=?"
                selectionArgs = arrayOf(ContentUris.parseId(uri).toString())
                rowsDeleted = db.delete(TABLE_NAME, selection, selectionArgs)
            }
            else -> throw IllegalArgumentException("Can't delete incorrect URI: $uri")
        }
        if (rowsDeleted != 0) context!!.contentResolver.notifyChange(uri, null)
        return rowsDeleted
    }

    override fun update(
        uri: Uri, values: ContentValues?, mSelection: String?,
        mSelectionArgs: Array<String>?
    ): Int {
        var selection = mSelection
        var selectionArgs = mSelectionArgs
        if (values!!.containsKey(COLUMN_NAME_ITEM_GOAL)) {
            val nameGoal = values.getAsString(COLUMN_NAME_ITEM_GOAL)
                ?: throw IllegalArgumentException("Input goal name")
        }
        if (values.containsKey(COLUMN_NAME_LIST)) {
            val nameList = values.getAsString(COLUMN_NAME_LIST)
                ?: throw IllegalArgumentException("Input list name")
        }
        val db: SQLiteDatabase = goalsDBHelper.writableDatabase
        val match = uriMatcher.match(uri)
        val rowsUpdated: Int
        when (match) {
            MATCHER_WHOLE_TABLE ->
                rowsUpdated = db.update(TABLE_NAME, values, selection, selectionArgs)
            MATCHER_GOAL -> {
                selection = "$_ID=?"
                selectionArgs = arrayOf(ContentUris.parseId(uri).toString())
                rowsUpdated = db.update(TABLE_NAME, values, selection, selectionArgs)
            }
            else -> throw IllegalArgumentException("Can't update incorrect URI: $uri")
        }
        if (rowsUpdated != 0) context!!.contentResolver.notifyChange(uri, null)
        return rowsUpdated
    }

    override fun getType(uri: Uri): String? {
        return when (uriMatcher.match(uri)) {
            MATCHER_WHOLE_TABLE -> CONTENT_MULTIPLE_ITEMS
            MATCHER_GOAL -> CONTENT_SINGLE_ITEM
            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }
    }

    @SuppressLint("Recycle")
    fun getAllItemsListGoals(mContext: Context): List<Goal> {
        val itemsGoalList: MutableList<Goal> = mutableListOf()
        val selectQuery = "SELECT * FROM $TABLE_NAME"
        goalsDBHelper = GoalDBOpenHelper(mContext)
        val db: SQLiteDatabase = goalsDBHelper.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)

        while (cursor.moveToNext()) {
            val indexId = cursor.getColumnIndex(_ID)
            val indexList = cursor.getColumnIndex(COLUMN_NAME_LIST)
            val indexGoal = cursor.getColumnIndex(COLUMN_NAME_ITEM_GOAL)
            val indexIcon = cursor.getColumnIndex(COLUMN_NAME_ICON_GOAL)
            val gid = cursor.getInt(indexId)
            val list = cursor.getString(indexList)
            val name = cursor.getString(indexGoal)
            val icon = cursor.getString(indexIcon)
            val goal = Goal(gid, list, name, icon)
            itemsGoalList.add(goal)
        }
        return itemsGoalList.toList()
    }

    @SuppressLint("Recycle")
    fun getGoalsByListName(mContext: Context, nameList: String?): List<Goal> {
        val itemsGoalList: MutableList<Goal> = mutableListOf()
        val selectQuery = when {
            nameList != null -> "SELECT * FROM $TABLE_NAME WHERE $COLUMN_NAME_LIST = ?"
            else -> "SELECT * FROM $TABLE_NAME"
        }
        val selectionArgs = when {
            nameList != null -> arrayOf(nameList)
            else -> null
        }
        goalsDBHelper = GoalDBOpenHelper(mContext)
        val db = goalsDBHelper.writableDatabase
        val cursor = db.rawQuery(selectQuery, selectionArgs)
        while (cursor.moveToNext()) {
            val indexId = cursor.getColumnIndex(_ID)
            val indexList = cursor.getColumnIndex(COLUMN_NAME_LIST)
            val indexGoal = cursor.getColumnIndex(COLUMN_NAME_ITEM_GOAL)
            val indexIcon = cursor.getColumnIndex(COLUMN_NAME_ICON_GOAL)
            val gid = cursor.getInt(indexId)
            val list = cursor.getString(indexList)
            val name = cursor.getString(indexGoal)
            val icon = cursor.getString(indexIcon)
            val goal = Goal(gid, list, name, icon)
            itemsGoalList.add(goal)
        }
        return itemsGoalList.toList()
    }
}