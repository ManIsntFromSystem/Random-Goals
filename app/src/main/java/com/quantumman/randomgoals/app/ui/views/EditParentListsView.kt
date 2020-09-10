package com.quantumman.randomgoals.app.ui.views

import com.quantumman.randomgoals.app.model.GoalItem
import com.quantumman.randomgoals.app.model.ParentWithListGoals
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(value = AddToEndSingleStrategy::class)
interface EditParentListsView : MvpView{

    @StateStrategyType(value = OneExecutionStateStrategy::class)
    fun initShowingData()

    @StateStrategyType(value = OneExecutionStateStrategy::class)
    fun saveWholeParentList(parentWithListGoals: ParentWithListGoals)

    fun editParentName(newName: String)
    fun saveNewGoalItem(goal: GoalItem)
    fun deleteGoalItem(goal: GoalItem)
    fun deleteAllGoals()

    fun showError(message: String)
}
