package com.quantumman.randomgoals.app.interactors

import com.quantumman.randomgoals.app.model.ParentWithListGoals
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable

interface ParentWithGoalsInteractor {

    fun getAllParentsWithListGoals(): Flowable<List<ParentWithListGoals>>

    fun deleteParentList(parent: ParentWithListGoals): Completable

    fun deleteAllParentLists(): Completable
}