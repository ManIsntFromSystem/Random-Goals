package com.quantumman.randomgoals.app.interactors

import com.quantumman.randomgoals.app.model.GoalItem
import com.quantumman.randomgoals.app.model.ParentWithListGoals
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Maybe

interface EditParentListWithGoalsInteractor {

    fun getParentListWithGoals(parentId: Long): Flowable<ParentWithListGoals>

    fun getAllParentNames(): Flowable<List<String>>

    fun updateParentList(parent: ParentWithListGoals): Completable

    fun deleteParentList(parent: ParentWithListGoals): Completable


    fun insertGoal(goal: GoalItem): Maybe<Long>

    fun updateGoal(goal: GoalItem): Completable

    fun deleteGoal(goal: GoalItem): Completable

    fun deleteAllGoals(parentId: Long): Completable
}