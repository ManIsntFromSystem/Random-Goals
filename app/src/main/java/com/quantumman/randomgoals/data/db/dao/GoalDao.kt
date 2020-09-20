package com.quantumman.randomgoals.data.db.dao

import androidx.room.*
import com.quantumman.randomgoals.data.model.GoalDto
import com.quantumman.randomgoals.helpers.contracts.DataContract.DELETE_ALL_GOALS
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe

@Dao
interface GoalDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGoal(goalDto: GoalDto): Completable

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateGoal(goalDto: GoalDto): Completable

    @Delete
    fun deleteGoal(goalDto: GoalDto): Completable

    @Query(DELETE_ALL_GOALS)
    fun deleteAllGoal(parentId: Long): Completable
}