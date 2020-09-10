package com.quantumman.randomgoals.helpers.contracts

import com.quantumman.randomgoals.BuildConfig

object DataContract {
    //SharedPref
    const val PREF_NAME = BuildConfig.APPLICATION_ID + "_pref"

    //DB
    const val DB_VERSION = 1
    const val DB_NAME = BuildConfig.APPLICATION_ID + ".db"
    const val TABLE_PARENT_LIST = "parent_list"
    const val TABLE_GOAL = "goals"

    //DAO Queries
    const val QUERY_GET_ALL_PARENT_LISTS = "SELECT * FROM $TABLE_PARENT_LIST"
    const val QUERY_GET_ALL_GOALS = "SELECT * FROM $TABLE_GOAL WHERE list_id = :parentId"
    const val QUERY_GET_PARENT_WITH_GOALS = "SELECT * FROM $TABLE_PARENT_LIST WHERE parent_id = :parentId"
    const val DELETE_PARENT_WITH_GOALS = "DELETE FROM $TABLE_PARENT_LIST WHERE parent_id = :parentId"
    const val QUERY_GET_ALL_PARENTS_WITH_GOALS = "SELECT * FROM $TABLE_PARENT_LIST"
}