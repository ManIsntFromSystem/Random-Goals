package com.quantumman.randomgoals.app.interactors

import com.quantumman.randomgoals.app.model.GoalItem
import com.quantumman.randomgoals.app.model.ParentWithListGoals
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single

interface EditParentListWithGoalsInteractor {

    fun insertParentListGoals(parent: ParentWithListGoals): Completable

    fun getParentListWithGoals(parentName: String): Flowable<ParentWithListGoals>

    fun getAllParentNames(): Maybe<List<String>>

    fun updateParentList(parent: ParentWithListGoals): Completable

    fun deleteParentList(parent: ParentWithListGoals): Completable


    fun insertGoal(goal: GoalItem): Completable

    fun updateGoal(goal: GoalItem): Completable

    fun deleteGoal(goal: GoalItem): Completable

    fun deleteAllGoals(parentId: Long): Completable
}