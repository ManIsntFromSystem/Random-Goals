package com.quantumman.randomgoals.app.ui.views

import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle

@AddToEndSingle
interface EditParentListsView : MvpView{

    fun startListeningData()
    fun changeStateRecycler(existence: Boolean)
    fun initComponents()
    fun showError(message: String)
    fun showError(message: Int)
}
