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

    companion object {
        private const val SQL_CREATE_TABLE = ("CREATE TABLE $TABLE_NAME ($_ID INTEGER PRIMARY KEY, " +
                "$COLUMN_NAME_LIST TEXT, $COLUMN_NAME_ITEM_GOAL TEXT, $COLUMN_NAME_ICON_GOAL TEXT)")
        private const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS $TABLE_NAME"
    }
}
    /*
    @Throws(SQLiteConstraintException::class)
    fun addGoal(name: Goal) {
        val values = ContentValues()
        values.put(COLUMN_NAME_ITEM_GOAL, name.nameGoal)
        values.put(COLUMN_NAME_ICON_GOAL, name.iconGoal)
        values.put(COLUMN_NAME_LIST, name.nameList)
        val db = this.writableDatabase
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    @SuppressLint("Recycle")
    fun getAllName(): ArrayList<Goal> {
        val goals = ArrayList<Goal>()
        val db = readableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("select * from ${GoalsContract.MemberEntry.TABLE_NAME}", null)
        } catch (e: SQLiteException) {
            db.execSQL(SQL_CREATE_TABLE)
            return ArrayList()
        }

        var goalId: String
        var nameList: String
        var nameGoal: String
        var iconGoal: String
        if (cursor!!.moveToFirst()) {
            while (!cursor.isAfterLast) {
                goalId = cursor.getString(cursor.getColumnIndex(GoalsContract.MemberEntry._ID))
                nameList = cursor.getString(cursor.getColumnIndex(GoalsContract.MemberEntry.COLUMN_NAME_LIST))
                nameGoal = cursor.getString(cursor.getColumnIndex(GoalsContract.MemberEntry.COLUMN_NAME_ITEM_GOAL))
                iconGoal = cursor.getString(cursor.getColumnIndex(GoalsContract.MemberEntry.COLUMN_NAME_ICON_GOAL))

                goals.add(Goal(goalId.toInt(), nameGoal, iconGoal, nameList))
                cursor.moveToNext()
            }
        }
        return goals
    }
*/