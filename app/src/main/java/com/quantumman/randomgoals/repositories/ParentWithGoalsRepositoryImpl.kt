package com.quantumman.randomgoals.repositories

import com.quantumman.randomgoals.app.model.GoalItem
import com.quantumman.randomgoals.app.model.ParentWithListGoals
import com.quantumman.randomgoals.data.db.dao.ParentWithGoalsListDao
import com.quantumman.randomgoals.data.model.ParentWithListGoalsEntity
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber
import java.util.concurrent.TimeUnit

class ParentWithGoalsRepositoryImpl(
    private val parentWithGoalsListDao: ParentWithGoalsListDao
) : ParentWithGoalsRepository {

    override fun getParentWithListById(parentId: Long): Flowable<ParentWithListGoals> =
        parentWithGoalsListDao.getParentWithList(parentId = parentId)
            .debounce(300, TimeUnit.MILLISECONDS)
            .observeOn(Schedulers.computation())
            .map { mapToParentWithListGoal(it) }
            .doOnNext { Timber.i("Next: $it") }
            .doOnError { Timber.e(it) }

    override fun getAllParentsWithListGoals(): Flowable<List<ParentWithListGoals>> {
        return Flowable.fromArray(mutableListOf<ParentWithListGoals>().apply {
            parentWithGoalsListDao.getAllParentsWithList()
                .observeOn(Schedulers.computation())
                .map { parent ->  mapToParentWithListGoal(parent)}
                .doOnNext { Timber.i("Next: $it") }
                .doOnError { Timber.e(it) }
                .subscribe { add(it) }
        }.toList())
    }

    override fun deleteParentListById(parent: ParentWithListGoalsEntity): Completable {
        return parentWithGoalsListDao.deleteParentListById(1)
    }

    private fun mapToParentWithListGoal(parentGoals: ParentWithListGoalsEntity): ParentWithListGoals {
        val parent = parentGoals.parent
        val listGoals = parentGoals.parentWithListGoals.map { goal ->
            GoalItem(
                goalId = goal.goalId,
                goalName = goal.goalName,
                goalDate = goal.goalDate,
                parentList = goal.listId
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
}