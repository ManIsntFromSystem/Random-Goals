package com.quantumman.randomgoals.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import com.quantumman.randomgoals.helpers.contracts.DataContract.TABLE_GOAL

@Entity(
    tableName = TABLE_GOAL,
    foreignKeys = [ForeignKey(
        entity = ParentListDto::class,
        parentColumns = ["parent_id"],
        childColumns = ["parent_list"],
        onDelete = CASCADE
    )]
)
data class GoalDto (@PrimaryKey(autoGenerate = true) @ColumnInfo(name = "goal_id") val goalId: Long,
                    @ColumnInfo(name = "goal_date") val goalDate: Long,
                    @ColumnInfo(name = "goal_name") val goalName: String,
                    @ColumnInfo(name = "parent_list") val parentList: Long)