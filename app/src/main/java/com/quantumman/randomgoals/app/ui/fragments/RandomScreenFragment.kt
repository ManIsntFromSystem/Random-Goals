package com.quantumman.randomgoals.app.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.quantumman.randomgoals.R
import com.quantumman.randomgoals.app.model.GoalItem
import com.quantumman.randomgoals.app.ui.presenters.RandomScreenPresenter
import com.quantumman.randomgoals.app.ui.views.RandomScreenView
import kotlinx.android.synthetic.main.random_screen_fragment.*
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import org.koin.android.ext.android.get

class RandomScreenFragment : MvpAppCompatFragment(R.layout.random_screen_fragment), RandomScreenView {

    @InjectPresenter lateinit var randomScreenPresenter: RandomScreenPresenter
    @ProvidePresenter
    fun provide(): RandomScreenPresenter = get()

    private var randomGoal: GoalItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        randomScreenPresenter.showData()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun prepareComponents() {
        ivNavToAllParentLists.setOnClickListener {
                val fragment = AllParentListsFragment.getInstance()
                navigate(fragment)
            }
    }

    override fun showData() {
        TODO("Not yet implemented")
    }

    override fun showRandomResult() {
        TODO("Not yet implemented")
    }

    override fun showError(message: String) {
        TODO("Not yet implemented")
    }

    override fun navigate(fragment: Fragment) {
        childFragmentManager.beginTransaction()
            .replace(R.id.container, fragment, TAG)
            .addToBackStack(TAG)
            .commit()
    }
    
    companion object {
        private val TAG = RandomScreenFragment::class.simpleName
        fun getInstance() = RandomScreenFragment()
    }
}