package com.quantumman.randomgoals.app.ui.fragments.containers

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.quantumman.randomgoals.app.ui.activities.MainActivity
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ru.terrakok.cicerone.commands.Command
import ru.terrakok.cicerone.commands.Forward

class CustomNavigator(
    activity: MainActivity,
    containerID: Int,
    private val changeScreenCallback: (Int) -> Unit
): SupportAppNavigator(activity, containerID) {
    val stackCount: Int
        get() = try {
            localStackCopy.size + 1
        } catch (e: Exception) {
            fragmentManager.backStackEntryCount + 1
        }

    override fun setupFragmentTransaction(
        command: Command,
        currentFragment: Fragment?,
        nextFragment: Fragment?,
        fragmentTransaction: FragmentTransaction
    ) {
        super.setupFragmentTransaction(
            command, currentFragment, nextFragment,
            fragmentTransaction.setTransition(
                if (command is Forward)
                    FragmentTransaction.TRANSIT_FRAGMENT_OPEN
                else
                    FragmentTransaction.TRANSIT_FRAGMENT_CLOSE
            )
        )
    }

    override fun applyCommands(commands: Array<out Command>) {
        super.applyCommands(commands)
        changeScreenCallback(stackCount)
    }

}
