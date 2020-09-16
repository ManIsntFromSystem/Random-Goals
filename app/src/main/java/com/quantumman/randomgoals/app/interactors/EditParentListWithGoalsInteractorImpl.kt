package com.quantumman.randomgoals.app.interactors

import com.quantumman.randomgoals.app.model.GoalItem
import com.quantumman.randomgoals.app.model.ParentWithListGoals
import com.quantumman.randomgoals.data.repositories.ParentWithGoalsRepository
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Maybe

class EditParentListWithGoalsInteractorImpl(
    private val parentWithGoalsRepo: ParentWithGoalsRepository
) : EditParentListWithGoalsInteractor {

    // MARK -> ParentList

    override fun getParentListWithGoals(parentId: Long): Flowable<ParentWithListGoals> {
        return parentWithGoalsRepo.getParentWithListById(parentId)
    }

    override fun getAllParentNames(): Flowable<List<String>> {
        return parentWithGoalsRepo.getAllParentNames()
    }

    override fun updateParentList(parent: ParentWithListGoals): Completable {
        return parentWithGoalsRepo.updateParentList(parent)
    }

    override fun deleteParentList(parent: ParentWithListGoals): Completable {
        return parentWithGoalsRepo.deleteParentList(parent)
    }

    // MARK -> Goals

    override fun insertGoal(goal: GoalItem): Maybe<Long> {
        return parentWithGoalsRepo.insertGoal(goal)
    }

    override fun updateGoal(goal: GoalItem): Completable {
        return parentWithGoalsRepo.updateGoal(goal)
    }

    override fun deleteGoal(goal: GoalItem): Completable {
        return parentWithGoalsRepo.deleteGoal(goal)
    }

    override fun deleteAllGoals(parentId: Long): Completable {
        return parentWithGoalsRepo.deleteAllGoals(parentId)
    }
}