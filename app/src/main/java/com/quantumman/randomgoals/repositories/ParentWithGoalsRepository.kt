package com.quantumman.randomgoals.repositories

import com.quantumman.randomgoals.app.model.ParentWithListGoals
import com.quantumman.randomgoals.data.model.ParentWithListGoalsEntity
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable

interface ParentWithGoalsRepository {

    fun getParentWithListById(parentId: Long): Flowable<ParentWithListGoals>

    fun getAllParentsWithListGoals(): Flowable<List<ParentWithListGoals>>

    fun deleteParentListById(parent: ParentWithListGoalsEntity): Completable

}