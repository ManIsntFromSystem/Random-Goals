package com.quantumman.randomgoals.data.repositories

import com.quantumman.randomgoals.app.model.GoalItem
import com.quantumman.randomgoals.app.model.ParentWithListGoals
import com.quantumman.randomgoals.data.db.dao.GoalDao
import com.quantumman.randomgoals.data.db.dao.ParentListsDao
import com.quantumman.randomgoals.data.db.dao.ParentWithGoalsListDao
import com.quantumman.randomgoals.data.model.GoalDto
import com.quantumman.randomgoals.data.model.ParentListDto
import com.quantumman.randomgoals.data.model.ParentWithListGoalsDto
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber
import java.util.concurrent.TimeUnit

class ParentWithGoalsRepositoryImpl(
    private val goalDao: GoalDao,
    private val parentListsDao: ParentListsDao,
    private val parentWithGoalsListDao: ParentWithGoalsListDao
) : ParentWithGoalsRepository {

    // Relation queries
    override fun getParentWithListByName(parentName: String): Flowable<ParentWithListGoals> =
        parentWithGoalsListDao.getParentWithList(parentName = parentName)
            .map { mapToParentWithListGoals(it) }
            .doOnError { Timber.e(it.localizedMessage) }

    override fun getAllParentsWithListGoals(): Flowable<List<ParentWithListGoals>> {
        return parentWithGoalsListDao.getAllParentsWithList()
            .flatMap { Flowable.fromIterable(it) }
            .map { mapToParentWithListGoals(it) }
            .doOnError { Timber.e(it.localizedMessage) }
            .buffer(50)
            .map { it.toList() }
    }

    //MARK :  Parents functions
    override fun insertParentListGoals(parent: ParentWithListGoals): Completable =
                                parentListsDao.insertParentListGoals(mapToParentListDto(parent))

    override fun getAllParentNames(): Maybe<List<String>> = parentListsDao.getAllParentNames()

    override fun updateParentList(parent: ParentWithListGoals): Completable =
                                parentListsDao.updateParentList(mapToParentListDto(parent))

    override fun deleteParentList(parent: ParentWithListGoals): Completable =
                                parentListsDao.deleteParentList(parent = mapToParentListDto(parent))

    override fun deleteAllParentLists(): Completable = parentListsDao.deleteAllParentLists()

    //MARK :  Goals functions
    override fun insertGoal(goal: GoalItem): Completable = goalDao.insertGoal(mapToGoalDto(goal))

    override fun updateGoal(goal: GoalItem): Completable = goalDao.updateGoal(mapToGoalDto(goal))

    override fun deleteGoal(goal: GoalItem): Completable = goalDao.deleteGoal(mapToGoalDto(goal))

    override fun deleteAllGoals(parentId: Long): Completable = goalDao.deleteAllGoal(parentId)

    // MARK :  Map functions
    private fun mapToParentWithListGoals(parentList: ParentWithListGoalsDto): ParentWithListGoals {
        val parent = parentList.parent
        val listGoals = parentList.listGoals.map { goal ->
            GoalItem(
                goalId = goal.goalId,
                goalName = goal.goalName,
                goalDate = goal.goalDate,
                parentList = goal.parentList
            )
        }
        return ParentWithListGoals(
            parentId = parent.parentId,
            parentName = parent.parentName,
            parentDate = parent.parentDate,
            parentIcon = parent.parentIcon,
            listGoals = listGoals
        )
    }

    private fun mapToGoalDto(goal: GoalItem): GoalDto = GoalDto(
        goalId = goal.goalId,
        goalName = goal.goalName,
        goalDate = goal.goalDate,
        parentList = goal.parentList
    )

    private fun mapToParentListDto(parent: ParentWithListGoals): ParentListDto = ParentListDto(
        parentId = parent.parentId,
        parentName = parent.parentName,
        parentDate = parent.parentDate,
        parentIcon = parent.parentIcon
    )
}

//        return Flowable.fromArray(mutableListOf<ParentWithListGoals>().apply {
//            parentWithGoalsListDao.getAllParentsWithList()
//                .observeOn(Schedulers.computation())
//                .map { parent -> mapToParentWithListGoals(parent) }
//                .subscribe { add(it) }
//        }.toList())
//    }