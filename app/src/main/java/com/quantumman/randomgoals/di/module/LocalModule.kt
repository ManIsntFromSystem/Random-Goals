package com.quantumman.randomgoals.di.module

import androidx.room.Room
import com.quantumman.randomgoals.data.db.AppDatabase
import com.quantumman.randomgoals.data.db.dao.ParentListsDao
import com.quantumman.randomgoals.helpers.contracts.DataContract.DB_NAME
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import org.koin.core.module.Module
import org.koin.dsl.module

//Koin DB Module
val roomModule: Module = module {
     single {
         Room.databaseBuilder(get(), AppDatabase::class.java, DB_NAME)
             .fallbackToDestructiveMigration()
             .build()
     }

    single { get<AppDatabase>().parentWithGoalsListDao() }
    single { get<AppDatabase>().parentListDao() }
    single { get<AppDatabase>().goalDao() }
}