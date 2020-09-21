package com.quantumman.randomgoals.app.ui.presenters

import android.os.Handler
import android.os.Looper
import com.quantumman.randomgoals.app.ui.fragments.containers.Screens
import com.quantumman.randomgoals.app.ui.views.MainActivityView
import moxy.InjectViewState
import moxy.MvpPresenter
import ru.terrakok.cicerone.Router

@InjectViewState
class MainActivityPresenter(
    private val router: Router
) : MvpPresenter<MainActivityView>() {

    private var countBackTried = 1

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        router.navigateTo(Screens.splash())
        Handler(Looper.getMainLooper()).postDelayed({
            router.newRootScreen(Screens.randomScreen())
        }, 2000L)
    }

    fun handleOnBackPressed() {
        router.exit()
    }
}