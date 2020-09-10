package com.quantumman.randomgoals.app.ui.views

import androidx.fragment.app.Fragment
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(value = AddToEndSingleStrategy::class)
interface RandomScreenView : MvpView {
    fun showData()
    fun showRandomResult()
    fun prepareComponents()
    fun showError(message: String)
    fun navigate(fragment: Fragment)
}