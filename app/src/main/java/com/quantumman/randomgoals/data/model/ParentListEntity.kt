package com.quantumman.randomgoals.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.quantumman.randomgoals.helpers.contracts.DataContract.TABLE_PARENT_LIST

@Entity(tableName = TABLE_PARENT_LIST)
data class ParentListEntity (@PrimaryKey(autoGenerate = true) @ColumnInfo(name = "parent_id") val parentId: Long,
                             @ColumnInfo(name = "parent_date") val parentDate: Long,
                             @ColumnInfo(name = "parent_name") val parentName: String,
                             @ColumnInfo(name = "parent_icon") val parentIcon: String)