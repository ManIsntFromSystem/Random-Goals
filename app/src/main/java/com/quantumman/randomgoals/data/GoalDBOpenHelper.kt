package com.quantumman.randomgoals.data

import android.annotation.SuppressLint
import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.database.sqlite.*
import android.net.Uri
import com.quantumman.randomgoals.data.GoalsContract.DATABASE_NAME
import com.quantumman.randomgoals.data.GoalsContract.DATABASE_VERSION
import com.quantumman.randomgoals.data.GoalsContract.MemberEntry.COLUMN_NAME_ICON_GOAL
import com.quantumman.randomgoals.data.GoalsContract.MemberEntry.COLUMN_NAME_ITEM_GOAL
import com.quantumman.randomgoals.data.GoalsContract.MemberEntry.COLUMN_NAME_LIST
import com.quantumman.randomgoals.data.GoalsContract.MemberEntry.TABLE_NAME
import com.quantumman.randomgoals.data.GoalsContract.MemberEntry._ID
import com.quantumman.randomgoals.model.Goal
import java.util.jar.Attributes

class GoalDBOpenHelper internal constructor(context: Context?) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    private val mContext = context
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    @SuppressLint("Recycle")
    fun queryGoals(nameList: String?): List<Goal> {
        val itemsGoalList: MutableList<Goal> = mutableListOf()
        val selectQuery = when {
            nameList != null -> "SELECT * FROM $TABLE_NAME WHERE $COLUMN_NAME_LIST = ?"
            else -> "SELECT * FROM $TABLE_NAME"
        }
        val selectionArgs = when {
            nameList != null -> arrayOf(nameList)
            else -> null
        }
        val db = writableDatabase
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

    @SuppressLint("Recycle")
    fun delListGoalsByListName(uri: Uri, mSelection: String?, mSelectionArgs: Array<String>?): Int {
        val db = writableDatabase
        val rowsDeleted = when (uriMatcher.match(uri)) {
            MATCHER_WHOLE_TABLE -> db.delete(TABLE_NAME, mSelection, mSelectionArgs)
            MATCHER_GOAL -> {
                if (mSelection != null && mSelectionArgs != null) {
                    db.delete(TABLE_NAME, mSelection, mSelectionArgs)
                } else {
                    val selection = "$_ID=?"
                    val selectionArgs = arrayOf(ContentUris.parseId(uri).toString())
                    db.delete(TABLE_NAME, selection, selectionArgs)
                }
            }
            else -> throw IllegalArgumentException("Can't delete incorrect URI: $uri")
        }
        if (rowsDeleted != 0) mContext?.contentResolver?.notifyChange(uri, null)
        return rowsDeleted
    }

    companion object {
        private const val SQL_CREATE_TABLE = ("CREATE TABLE $TABLE_NAME (" +
                "$_ID INTEGER PRIMARY KEY, " +
                "$COLUMN_NAME_LIST TEXT, " +
                "$COLUMN_NAME_ITEM_GOAL TEXT, " +
                "$COLUMN_NAME_ICON_GOAL TEXT)")
        private const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS $TABLE_NAME"

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
}