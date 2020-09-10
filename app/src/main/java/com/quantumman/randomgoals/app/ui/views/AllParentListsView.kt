package com.quantumman.randomgoals.app.ui.views

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(value = AddToEndSingleStrategy::class)
interface AllParentListsView : MvpView {

    @StateStrategyType(value = OneExecutionStateStrategy::class)
    fun initShowingData()

    fun showError(message: String)
    fun deleteParentList()
}