package com.quantumman.randomgoals.helpers.contracts

import com.quantumman.randomgoals.BuildConfig

object DataContract {
    //SharedPref
    const val PREF_NAME = BuildConfig.APPLICATION_ID + "_pref"

    //DB Config
    const val DB_VERSION = 1
    const val DB_NAME = BuildConfig.APPLICATION_ID + ".db"
    const val TABLE_PARENT_LIST = "parent_list"
    const val TABLE_GOAL = "goals"

    //DAO Queries : Transition
    const val QUERY_GET_ALL_PARENTS_WITH_GOALS = "SELECT * FROM $TABLE_PARENT_LIST ORDER BY parent_date ASC"
    const val QUERY_GET_PARENT_WITH_GOALS_BY_ID = "SELECT * FROM $TABLE_PARENT_LIST WHERE parent_id = :parentId"

    //DAO Queries : Parent lists
    const val QUERY_GET_ALL_NAMES_OF_PARENTS = "SELECT parent_name FROM $TABLE_PARENT_LIST"
    const val DELETE_ALL_PARENT_LISTS = "DELETE FROM $TABLE_PARENT_LIST"
//    const val DELETE_PARENT_LIST_BY_ID = "DELETE FROM $TABLE_PARENT_LIST WHERE parent_id = :parentId"

    //DAO Queries : Goals
//    const val QUERY_GET_ALL_GOALS_BY_PARENT_ID = "SELECT * FROM $TABLE_GOAL WHERE list_id = :parentId"
    const val DELETE_ALL_GOALS = "DELETE FROM $TABLE_GOAL WHERE parent_list = :parentId"
//    const val DELETE_GOAL_BY_ID = "DELETE FROM $TABLE_GOAL WHERE goal_id = :goalId"
}