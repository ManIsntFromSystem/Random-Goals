package com.quantumman.randomgoals.data.db.dao

import androidx.room.*
import com.quantumman.randomgoals.data.model.ParentListEntity
import com.quantumman.randomgoals.helpers.contracts.DataContract.QUERY_GET_ALL_PARENT_LISTS
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Maybe

@Dao
interface ParentListsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertParentListGoals(parentList: ParentListEntity): Maybe<Long>

    @Query(QUERY_GET_ALL_PARENT_LISTS)
    fun getAllParentLists(): Flowable<List<ParentListEntity>>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateParentList(parentList: ParentListEntity): Completable
    @Delete
    fun deleteParentListGoals(parentList: ParentListEntity): Completable
}