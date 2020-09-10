package com.quantumman.randomgoals.repositories

import com.quantumman.randomgoals.data.db.dao.GoalDao
import com.quantumman.randomgoals.data.db.dao.ParentListsDao

class GoalsRepositoryImpl(
    private val goalDao: GoalDao,
    private val parentListsDao: ParentListsDao
): GoalsRepository {

}