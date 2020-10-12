package com.quantumman.randomgoals.app.ui.views

import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle

@AddToEndSingle
interface AllParentListsView : MvpView {

    fun initComponents()
    fun changeStateRecycler(existence: Boolean)

    fun showError(message: String)
    fun showError(message: Int)
}