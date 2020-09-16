package com.quantumman.randomgoals.data.repositories

import com.quantumman.randomgoals.app.model.ParentWithListGoals
import io.reactivex.rxjava3.core.Flowable

interface ParentWithGoalsRepository : ParentListRepository, GoalsRepository {

    fun getParentWithListById(parentId: Long): Flowable<ParentWithListGoals>

    fun getAllParentsWithListGoals(): Flowable<List<ParentWithListGoals>>
}