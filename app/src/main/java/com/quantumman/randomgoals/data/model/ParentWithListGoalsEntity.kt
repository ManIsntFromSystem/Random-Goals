package com.quantumman.randomgoals.data.model

import androidx.room.Embedded
import androidx.room.Relation

data class ParentWithListGoalsEntity(
    @Embedded val parent: ParentListEntity,
    @Relation(
        parentColumn = "parent_id",
        entityColumn = "list_id"
    )
    val parentWithListGoals: List<GoalEntity>
)