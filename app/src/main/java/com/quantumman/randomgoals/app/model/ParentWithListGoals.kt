package com.quantumman.randomgoals.app.model

data class ParentWithListGoals (
    val parentId: Long = 0,
    var parentDate: Long,
    var parentName: String,
    var parentIcon: Int,
    val listGoals: List<GoalItem>
)


data class GoalItem(
    val goalId: Long = 0,
    var goalDate: Long,
    val goalName: String,
    val parentList: Long
)