package com.quantumman.randomgoals.data

import android.content.ContentResolver
import android.net.Uri
import android.provider.BaseColumns

object GoalsContract {
    const val DATABASE_VERSION = 1
    const val DATABASE_NAME = "goals_data"
    private const val SCHEME = "content://"
    const val AUTHORITY = "com.quantumman.randomgoals"
    const val PATH_PRODUCTS = "goals"
    private val BASE_CONTENT_URI = Uri.parse(SCHEME + AUTHORITY)

    object MemberEntry : BaseColumns {
        const val TABLE_NAME = "goals"
        const val _ID = BaseColumns._ID
        const val COLUMN_NAME_LIST = "nameList"
        const val COLUMN_NAME_ITEM_GOAL = "nameGoal"
        const val COLUMN_NAME_ICON_GOAL = "nameIconGoal"
        @JvmField
        val CONTENT_URI: Uri = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_PRODUCTS)
        const val CONTENT_MULTIPLE_ITEMS = "${ContentResolver.CURSOR_DIR_BASE_TYPE}/$AUTHORITY/$PATH_PRODUCTS"
        const val CONTENT_SINGLE_ITEM = "${ContentResolver.CURSOR_ITEM_BASE_TYPE}/$AUTHORITY/$PATH_PRODUCTS"
    }
}