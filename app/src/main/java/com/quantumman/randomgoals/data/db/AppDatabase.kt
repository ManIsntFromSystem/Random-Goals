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

//    companion object {
//        @Volatile
//        private var INSTANCE: AppDatabase? = null
//
//        fun getInstance(context: Context): AppDatabase {
//            val tempInstance = INSTANCE
//            if (tempInstance != null) return tempInstance
//
//            synchronized(this) {
//                val instance = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, DB_NAME)
//                    .fallbackToDestructiveMigration()
//                    .build()
//                INSTANCE = instance
//                return instance
//            }
//        }
//    }
}