package com.quantumman.randomgoals.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.quantumman.randomgoals.helpers.contracts.DataContract.TABLE_GOAL

@Entity(tableName = TABLE_GOAL)
data class GoalEntity (@PrimaryKey(autoGenerate = true) @ColumnInfo(name = "goal_id") val goalId: Long,
                       @ColumnInfo(name = "goal_date") val goalDate: Long,
                       @ColumnInfo(name = "goal_name") val goalName: String,
                       @ColumnInfo(name = "list_id") val listId: Long)