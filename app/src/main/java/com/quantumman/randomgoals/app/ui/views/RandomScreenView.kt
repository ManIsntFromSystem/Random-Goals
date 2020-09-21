package com.quantumman.randomgoals.app.ui.views

import com.quantumman.randomgoals.app.model.GoalItem
import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle

@AddToEndSingle
interface RandomScreenView : MvpView {
    fun prepareComponents()
    fun showError(message: String)
    fun showError(message: Int)
    fun showRandomResult(goal: GoalItem, icon: Int)
}