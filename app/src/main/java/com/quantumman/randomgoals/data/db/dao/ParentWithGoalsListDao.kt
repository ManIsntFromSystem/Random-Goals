package com.quantumman.randomgoals.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Transaction
import com.quantumman.randomgoals.data.model.ParentWithListGoalsEntity
import com.quantumman.randomgoals.helpers.contracts.DataContract
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable

@Dao
interface ParentWithGoalsListDao {
    @Transaction
    @Query(DataContract.QUERY_GET_PARENT_WITH_GOALS)
    fun getParentWithList(parentId: Long): Flowable<ParentWithListGoalsEntity>

    @Transaction
    @Query(DataContract.QUERY_GET_ALL_PARENTS_WITH_GOALS)
    fun getAllParentsWithList(): Flowable<ParentWithListGoalsEntity>

    @Transaction
    @Query(DataContract.DELETE_PARENT_WITH_GOALS)
    fun deleteParentListById(parentId: Long): Completable
}