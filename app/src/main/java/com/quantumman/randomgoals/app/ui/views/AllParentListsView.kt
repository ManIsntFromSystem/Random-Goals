package com.quantumman.randomgoals.app.ui.views

import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle
import moxy.viewstate.strategy.alias.OneExecution
import moxy.viewstate.strategy.alias.Skip

@AddToEndSingle
interface AllParentListsView : MvpView {

    fun initShowingData()
    @Skip
    fun showError(message: String)
    @OneExecution
    fun deleteParentList()
}