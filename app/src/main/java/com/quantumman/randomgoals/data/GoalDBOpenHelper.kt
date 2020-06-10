package com.quantumman.randomgoals.data

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.*
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
    val mContext = context
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
    fun getAllItemsListGoals(): List<Goal> {
        val itemsGoalList: MutableList<Goal> = mutableListOf()
        val selectQuery = "SELECT * FROM $TABLE_NAME"
        val db: SQLiteDatabase = writableDatabase
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
    fun getGoalsByListName(nameList: String?): List<Goal> {
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

    companion object {
        private const val SQL_CREATE_TABLE = ("CREATE TABLE $TABLE_NAME (" +
                "$_ID INTEGER PRIMARY KEY, " +
                "$COLUMN_NAME_LIST TEXT, " +
                "$COLUMN_NAME_ITEM_GOAL TEXT, " +
                "$COLUMN_NAME_ICON_GOAL TEXT)")
        private const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS $TABLE_NAME"
    }
}