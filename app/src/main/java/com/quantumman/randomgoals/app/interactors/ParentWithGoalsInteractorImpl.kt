package com.quantumman.randomgoals.app.interactors

import com.quantumman.randomgoals.app.model.ParentWithListGoals
import com.quantumman.randomgoals.data.repositories.ParentListRepository
import com.quantumman.randomgoals.data.repositories.ParentWithGoalsRepository
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable

class ParentWithGoalsInteractorImpl(
    private val parentWithGoalsRepo: ParentWithGoalsRepository
) : ParentWithGoalsInteractor {

    override fun getAllParentsWithListGoals(): Flowable<List<ParentWithListGoals>> {
        return parentWithGoalsRepo.getAllParentsWithListGoals()
    }

    override fun deleteParentList(parent: ParentWithListGoals): Completable {
        return parentWithGoalsRepo.deleteParentList(parent)
    }

    override fun deleteAllParentLists(): Completable {
        return parentWithGoalsRepo.deleteAllParentLists()
    }
}