package com.quantumman.randomgoals.data.repositories

import com.quantumman.randomgoals.app.model.ParentWithListGoals
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Maybe

interface ParentListRepository {

    fun insertParentListGoals(parent: ParentWithListGoals): Maybe<Long>

    fun getAllParentNames(): Flowable<List<String>>

    fun updateParentList(parent: ParentWithListGoals): Completable

    fun deleteParentList(parent: ParentWithListGoals): Completable

    fun deleteAllParentLists(): Completable
}