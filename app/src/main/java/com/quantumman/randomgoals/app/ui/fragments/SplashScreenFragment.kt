package com.quantumman.randomgoals.app.ui.fragments

import androidx.fragment.app.Fragment
import com.quantumman.randomgoals.R
import com.quantumman.randomgoals.app.ui.presenters.SplashScreenPresenter
import com.quantumman.randomgoals.app.ui.views.SplashScreenView
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import org.koin.android.ext.android.get

class SplashScreenFragment : Fragment(R.layout.splash_screen_layout), SplashScreenView {

    @InjectPresenter
    lateinit var splashPresenter: SplashScreenPresenter

    @ProvidePresenter
    fun provide(): SplashScreenPresenter = get()

    override fun showLoading() {

    }

    companion object {
        fun getInstance() = SplashScreenFragment()
    }
}