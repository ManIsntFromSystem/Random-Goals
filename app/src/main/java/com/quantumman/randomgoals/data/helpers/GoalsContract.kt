package com.quantumman.randomgoals.data.helpers

import android.content.ContentResolver
import android.net.Uri
import android.provider.BaseColumns

object GoalsContract {
    const val DATABASE_VERSION = 2
    const val DATABASE_NAME = "goals_data"
    private const val SCHEME = "content://"
    const val AUTHORITY = "com.quantumman.randomgoals"
    private val BASE_CONTENT_URI = Uri.parse(SCHEME + AUTHORITY)
    const val PATH_GOAL = "item_goal"
    const val PATH_LIST = "goals_list"

    object ItemGoalEntry : BaseColumns {
        const val TABLE_NAME_GOAL = "item_goal"
        const val ID_GOAL = BaseColumns._ID
        const val COLUMN_NAME_GOAL = "nameGoal"
        const val COLUMN_ID_LIST = "goalsList"
        @JvmField
        val CONTENT_URI_GOAL: Uri = Uri.withAppendedPath(
            BASE_CONTENT_URI,
            PATH_GOAL
        )
        const val CONTENT_MULTIPLE_GOALS = "${ContentResolver.CURSOR_DIR_BASE_TYPE}/$AUTHORITY/$PATH_GOAL"
        const val CONTENT_SINGLE_GOAL = "${ContentResolver.CURSOR_ITEM_BASE_TYPE}/$AUTHORITY/$PATH_GOAL"
    }
    object GoalsListEntry : BaseColumns {
        const val TABLE_NAME_LIST = "goals_list"
        const val ID_LIST = BaseColumns._ID
        const val COLUMN_NAME_LIST = "nameList"
        const val COLUMN_ICON_GOAL = "nameIconGoal"
        @JvmField
        val CONTENT_URI_LIST: Uri = Uri.withAppendedPath(
            BASE_CONTENT_URI,
            PATH_LIST
        )
        const val CONTENT_MULTIPLE_LISTS = "${ContentResolver.CURSOR_DIR_BASE_TYPE}/$AUTHORITY/$PATH_LIST"
        const val CONTENT_SINGLE_LIST = "${ContentResolver.CURSOR_ITEM_BASE_TYPE}/$AUTHORITY/$PATH_LIST"
    }
}