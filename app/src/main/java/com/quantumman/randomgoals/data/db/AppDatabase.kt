package com.quantumman.randomgoals.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.quantumman.randomgoals.data.db.dao.GoalDao
import com.quantumman.randomgoals.data.db.dao.ParentListsDao
import com.quantumman.randomgoals.data.db.dao.ParentWithGoalsListDao
import com.quantumman.randomgoals.data.model.GoalDto
import com.quantumman.randomgoals.data.model.ParentListDto
import com.quantumman.randomgoals.helpers.contracts.DataContract.DB_VERSION

@Database(entities = [GoalDto::class, ParentListDto::class], version = DB_VERSION, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun goalDao(): GoalDao
    abstract fun parentListDao(): ParentListsDao
    abstract fun parentWithGoalsListDao(): ParentWithGoalsListDao
}