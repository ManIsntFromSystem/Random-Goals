package com.quantumman.randomgoals.app.ui.views

import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle

interface SplashScreenView : MvpView {
    @AddToEndSingle
    fun showLoading()
}