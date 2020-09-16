package com.quantumman.randomgoals.data.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class ParentWithListGoalsDto(
    @Embedded val parent: ParentListDto,
    @Relation(
        parentColumn = "parent_id",
        entityColumn = "parent_list"
    )
    val listGoals: List<GoalDto>
)