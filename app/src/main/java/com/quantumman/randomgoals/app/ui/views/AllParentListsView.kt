package com.quantumman.randomgoals.app.ui.views

import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle
import moxy.viewstate.strategy.alias.OneExecution

@OneExecution
interface AllParentListsView : MvpView {

    @AddToEndSingle
    fun initComponents()

    fun showError(message: String)
    fun showError(message: Int)
    fun changeStateRecycler(existence: Boolean)
}