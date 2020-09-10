package com.quantumman.randomgoals.data.db.dao

import androidx.room.*
import com.quantumman.randomgoals.data.model.GoalEntity
import com.quantumman.randomgoals.helpers.contracts.DataContract.QUERY_GET_ALL_GOALS
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Maybe

@Dao
interface GoalDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertGoal(goalEntity: GoalEntity): Maybe<Long>

    @Query(QUERY_GET_ALL_GOALS)
    fun getAllGoalsByParentListId(parentId: Long): Flowable<List<GoalEntity>>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateGoal(goalEntity: GoalEntity): Completable
    @Delete
    fun deleteGoal(goalEntity: GoalEntity): Completable
    @Delete()
    fun deleteAllGoal(goalEntity: List<GoalEntity>): Completable
}