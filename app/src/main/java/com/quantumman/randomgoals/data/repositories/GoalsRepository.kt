package com.quantumman.randomgoals.data.repositories

import com.quantumman.randomgoals.app.model.GoalItem
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe

interface GoalsRepository {

    fun insertGoal(goal: GoalItem): Maybe<Long>

    fun updateGoal(goal: GoalItem): Completable

    fun deleteGoal(goal: GoalItem): Completable

    fun deleteAllGoals(parentId: Long): Completable
}