package com.quantumman.randomgoals.app.interactors

import com.quantumman.randomgoals.app.model.ParentWithListGoals
import com.quantumman.randomgoals.data.model.ParentWithListGoalsEntity
import com.quantumman.randomgoals.repositories.ParentWithGoalsRepository
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable

class ParentWithGoalsInteractorImpl(
    private val parentWithGoalsRepo: ParentWithGoalsRepository
) : ParentWithGoalsInteractor {

    override fun getParentWithListById(parentId: Long): Flowable<ParentWithListGoals> {
        return parentWithGoalsRepo.getParentWithListById(parentId = parentId)
    }

    override fun getAllParentsWithListGoals(): Flowable<List<ParentWithListGoals>> {
        return parentWithGoalsRepo.getAllParentsWithListGoals()
    }

    override fun deleteParentListById(parent: ParentWithListGoalsEntity): Completable {
        return parentWithGoalsRepo.deleteParentListById(parent)
    }
}