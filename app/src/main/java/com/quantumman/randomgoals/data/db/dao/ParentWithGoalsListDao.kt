package com.quantumman.randomgoals.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.quantumman.randomgoals.data.model.ParentWithListGoalsDto
import com.quantumman.randomgoals.helpers.contracts.DataContract.QUERY_GET_ALL_PARENTS_WITH_GOALS
import com.quantumman.randomgoals.helpers.contracts.DataContract.QUERY_GET_PARENT_WITH_GOALS_BY_NAME
import io.reactivex.rxjava3.core.Flowable

@Dao
interface ParentWithGoalsListDao {
    @Transaction
    @Query(QUERY_GET_PARENT_WITH_GOALS_BY_NAME)
    fun getParentWithList(parentName: String): Flowable<ParentWithListGoalsDto>

    @Transaction
    @Query(QUERY_GET_ALL_PARENTS_WITH_GOALS)
    fun getAllParentsWithList(): Flowable<List<ParentWithListGoalsDto>>
}