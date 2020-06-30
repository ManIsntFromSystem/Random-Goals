package com.quantumman.randomgoals.data.helpers

import android.annotation.SuppressLint
import android.content.ContentUris
import android.content.Context
import android.content.UriMatcher
import android.database.sqlite.*
import android.net.Uri
import com.quantumman.randomgoals.data.helpers.GoalsContract.DATABASE_NAME
import com.quantumman.randomgoals.data.helpers.GoalsContract.DATABASE_VERSION
import com.quantumman.randomgoals.data.helpers.GoalsContract.AUTHORITY
import com.quantumman.randomgoals.data.helpers.GoalsContract.PATH_GOAL
import com.quantumman.randomgoals.data.helpers.GoalsContract.ItemGoalEntry.TABLE_NAME_GOAL
import com.quantumman.randomgoals.data.helpers.GoalsContract.ItemGoalEntry.ID_GOAL
import com.quantumman.randomgoals.data.helpers.GoalsContract.ItemGoalEntry.COLUMN_NAME_GOAL
import com.quantumman.randomgoals.data.helpers.GoalsContract.ItemGoalEntry.COLUMN_ID_LIST
import com.quantumman.randomgoals.data.helpers.GoalsContract.GoalsListEntry.TABLE_NAME_LIST
import com.quantumman.randomgoals.data.helpers.GoalsContract.GoalsListEntry.ID_LIST
import com.quantumman.randomgoals.data.helpers.GoalsContract.GoalsListEntry.COLUMN_NAME_LIST
import com.quantumman.randomgoals.data.helpers.GoalsContract.GoalsListEntry.COLUMN_ICON_GOAL
import com.quantumman.randomgoals.app.model.Goal
import com.quantumman.randomgoals.app.model.ListNames

class GoalDBOpenHelper internal constructor(context: Context?) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    private val mContext = context
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_TABLE_GOALS)
        db.execSQL(SQL_CREATE_TABLE_LISTS)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(SQL_DELETE_GOALS)
        db.execSQL(SQL_DELETE_LISTS)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    @SuppressLint("Recycle")
    fun queryLists(id: Int?, inputName: String?): List<ListNames> {
        val itemsGoalList: MutableList<ListNames> = mutableListOf()
        val selectQuery = when {
            id != null -> "SELECT * FROM $TABLE_NAME_LIST WHERE $ID_LIST = ?"
            inputName != null -> "SELECT * FROM $TABLE_NAME_LIST WHERE $COLUMN_NAME_LIST = ?"
            else -> "SELECT * FROM $TABLE_NAME_LIST"
        }
        val selectionArgs = when {
            id != null -> arrayOf(id.toString())
            inputName != null -> arrayOf(inputName)
            else -> null
        }
        val db = writableDatabase
        val cursor = db.rawQuery(selectQuery, selectionArgs)
        while (cursor.moveToNext()) {
            val indexId = cursor.getColumnIndex(ID_LIST)
            val indexName = cursor.getColumnIndex(COLUMN_NAME_LIST)
            val indexIcon = cursor.getColumnIndex(COLUMN_ICON_GOAL)
            val gid = cursor.getInt(indexId)
            val name = cursor.getString(indexName)
            val icon = cursor.getInt(indexIcon)
            val listNames =
                ListNames(
                    gid,
                    name,
                    icon
                )
            itemsGoalList.add(listNames)
        }
        return itemsGoalList.toList()
    }

    @SuppressLint("Recycle")
    fun queryGoals(nameList: String?): List<Goal> {
        val itemsGoalList: MutableList<Goal> = mutableListOf()
        val selectQuery = when {
            nameList != null -> "SELECT * FROM $TABLE_NAME_GOAL WHERE $COLUMN_ID_LIST=?"
            else -> "SELECT * FROM $TABLE_NAME_GOAL"
        }
        val selectionArgs = when {
            nameList != null -> arrayOf(nameList)
            else -> null
        }
        val db = writableDatabase
        val cursor = db.rawQuery(selectQuery, selectionArgs)
        while (cursor.moveToNext()) {
            val indexId = cursor.getColumnIndex(ID_GOAL)
            val indexList = cursor.getColumnIndex(COLUMN_NAME_GOAL)
            val indexGoal = cursor.getColumnIndex(COLUMN_ID_LIST)
            val gid = cursor.getInt(indexId)
            val name = cursor.getString(indexList)
            val list = cursor.getInt(indexGoal)
            val goal =
                Goal(gid, name, list)
            itemsGoalList.add(goal)
        }
        return itemsGoalList.toList()
    }

    @SuppressLint("Recycle")
    fun delListGoalsByListName(uri: Uri, mSelection: String?, mSelectionArgs: Array<String>?): Int {
        val db = writableDatabase
        val rowsDeleted = when (uriMatcher.match(uri)) {
            MATCHER_WHOLE_TABLE -> db.delete(TABLE_NAME_GOAL, mSelection, mSelectionArgs)
            MATCHER_GOAL -> {
                if (mSelection != null && mSelectionArgs != null) {
                    db.delete(TABLE_NAME_GOAL, mSelection, mSelectionArgs)
                } else {
                    val selection = "$ID_GOAL=?"
                    val selectionArgs = arrayOf(ContentUris.parseId(uri).toString())
                    db.delete(TABLE_NAME_GOAL, selection, selectionArgs)
                }
            }
            else -> throw IllegalArgumentException("Can't delete incorrect URI: $uri")
        }
        if (rowsDeleted != 0) mContext?.contentResolver?.notifyChange(uri, null)
        return rowsDeleted
    }

    companion object {
        private const val SQL_CREATE_TABLE_GOALS = ("CREATE TABLE $TABLE_NAME_GOAL (" +
                "$ID_GOAL INTEGER PRIMARY KEY, " +
                "$COLUMN_NAME_GOAL TEXT , " +
                "$COLUMN_ID_LIST INTEGER, " +
                "FOREIGN KEY ($ID_GOAL) " +
                "REFERENCES $TABLE_NAME_LIST ($ID_LIST))")
        private const val SQL_DELETE_GOALS = "DROP TABLE IF EXISTS $TABLE_NAME_GOAL"

        private const val SQL_CREATE_TABLE_LISTS = ("CREATE TABLE $TABLE_NAME_LIST (" +
                "$ID_LIST INTEGER PRIMARY KEY, " +
                "$COLUMN_NAME_LIST TEXT UNIQUE NOT NULL, " +
                "$COLUMN_ICON_GOAL INTEGER)")
        private const val SQL_DELETE_LISTS = "DROP TABLE IF EXISTS $TABLE_NAME_GOAL"

        private const val MATCHER_WHOLE_TABLE = 333
        private const val MATCHER_GOAL = 777

        private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
            addURI(AUTHORITY, PATH_GOAL,
                MATCHER_WHOLE_TABLE
            )
            addURI(AUTHORITY,"${PATH_GOAL}/#",
                MATCHER_GOAL
            )
        }
    }
}