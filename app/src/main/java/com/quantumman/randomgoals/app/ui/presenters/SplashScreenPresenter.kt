package com.quantumman.randomgoals.app.ui.presenters

import com.quantumman.randomgoals.app.ui.views.SplashScreenView
import moxy.InjectViewState
import moxy.MvpPresenter
import ru.terrakok.cicerone.Router

@InjectViewState
class SplashScreenPresenter(
    private val router: Router
) : MvpPresenter<SplashScreenView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.showLoading()
    }
}