package com.quantumman.randomgoals.app.ui.activities

import com.quantumman.randomgoals.R
import com.quantumman.randomgoals.app.ui.fragments.containers.CustomNavigator
import com.quantumman.randomgoals.app.ui.presenters.MainActivityPresenter
import com.quantumman.randomgoals.app.ui.views.MainActivityView
import com.quantumman.randomgoals.helpers.HandleSnackMessage
import com.quantumman.randomgoals.helpers.extensions.showSnack
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import org.koin.android.ext.android.get
import ru.terrakok.cicerone.NavigatorHolder
import timber.log.Timber

class MainActivity : MvpAppCompatActivity(R.layout.activity_main), HandleSnackMessage, MainActivityView {

    @InjectPresenter lateinit var mainPresenter: MainActivityPresenter

    @ProvidePresenter fun provide(): MainActivityPresenter = get()

    private var navigatorHolder: NavigatorHolder = get()
    private var stackCount = 0

    private val navigator by lazy {
        CustomNavigator(this, R.id.container) { count ->
            stackCount += count
            Timber.i("stackCount : $stackCount")
        }
    }

    override fun onResume() {
        super.onResume()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onBackPressed() {
        mainPresenter.handleOnBackPressed()
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }

    override fun showMessage(message: String) {
        showSnack(text = message)
    }
}