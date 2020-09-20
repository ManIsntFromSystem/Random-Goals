package com.quantumman.randomgoals.data.db.dao

import androidx.room.*
import com.quantumman.randomgoals.data.model.ParentListDto
import com.quantumman.randomgoals.helpers.contracts.DataContract.DELETE_ALL_PARENT_LISTS
import com.quantumman.randomgoals.helpers.contracts.DataContract.QUERY_GET_ALL_NAMES_OF_PARENTS
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single

@Dao
interface ParentListsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertParentListGoals(parentList: ParentListDto): Completable

    @Query(QUERY_GET_ALL_NAMES_OF_PARENTS)
    fun getAllParentNames(): Maybe<List<String>>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateParentList(parentList: ParentListDto): Completable

    @Delete
    fun deleteParentList(parent: ParentListDto): Completable

    @Query(DELETE_ALL_PARENT_LISTS)
    fun deleteAllParentLists(): Completable
}