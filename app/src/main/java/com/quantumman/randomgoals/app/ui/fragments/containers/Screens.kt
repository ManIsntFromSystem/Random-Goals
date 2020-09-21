package com.quantumman.randomgoals.app.ui.fragments.containers

import androidx.fragment.app.Fragment
import com.quantumman.randomgoals.app.ui.fragments.AllParentListsFragment
import com.quantumman.randomgoals.app.ui.fragments.EditParentListFragment
import com.quantumman.randomgoals.app.ui.fragments.RandomScreenFragment
import com.quantumman.randomgoals.app.ui.fragments.SplashScreenFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

class Screens (private val fragment: Fragment): SupportAppScreen() {
    override fun getFragment() = fragment

    companion object {
        fun splash() = Screens(SplashScreenFragment.getInstance())
        fun randomScreen() = Screens(RandomScreenFragment.getInstance())
        fun allParentListsScreen() = Screens(AllParentListsFragment.getInstance())
        fun createParentListsScreen() = Screens(EditParentListFragment.getCreateParentInstance())
        fun editParentListsScreen(params: String) = Screens(EditParentListFragment.getEditParentInstance(parentName = params))
    }
}