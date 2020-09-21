package com.quantumman.randomgoals.app.ui.fragments

import android.os.Bundle
import android.view.View
import com.quantumman.randomgoals.R
import com.quantumman.randomgoals.app.model.GoalItem
import com.quantumman.randomgoals.app.ui.adapters.ListGoalsDropDownAdapter
import com.quantumman.randomgoals.app.ui.presenters.RandomScreenPresenter
import com.quantumman.randomgoals.app.ui.views.RandomScreenView
import com.quantumman.randomgoals.helpers.HandleSnackMessage
import kotlinx.android.synthetic.main.random_screen_layout.*
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import org.koin.android.ext.android.get


class RandomScreenFragment : MvpAppCompatFragment(R.layout.random_screen_layout), RandomScreenView {

    @InjectPresenter lateinit var randomScreenPresenter: RandomScreenPresenter
    @ProvidePresenter fun provide(): RandomScreenPresenter = get()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        randomScreenPresenter.showData()
    }

    override fun prepareComponents() {
        randomScreenPresenter.data.observe(viewLifecycleOwner, { list ->
            if (list != null) autoCompleteDropDown.setAdapter(ListGoalsDropDownAdapter(requireContext(), list))
        })

        ivNavToAllParentLists.setOnClickListener {
            randomScreenPresenter.clickedOnAllParentListsImg()
        }

        autoCompleteDropDown.setOnItemClickListener { _, _, i, _ ->
            randomScreenPresenter.clickedOnParentListInSpinner(i)
        }

        fabStartRandomGoal.setOnClickListener {
            randomScreenPresenter.clickedOnStartRandomGoal()
        }
    }

    override fun showRandomResult(goal: GoalItem, icon: Int) {
        iconChosenGoalImg.setImageResource(icon)
        nameChosenGoalTextView.text = goal.goalName
        startedRandomGoalImg.visibility = View.INVISIBLE
        relativeChosenGoal.visibility = View.VISIBLE
    }

    /////////////////*   Errors   *//////////////////

    override fun showError(message: String) {
        (activity as? HandleSnackMessage)?.showMessage(message = message)
    }

    override fun showError(message: Int) {
        (activity as? HandleSnackMessage)?.showMessage(message = getString(message))
    }
    
    companion object {
        private val TAG = RandomScreenFragment::class.simpleName
        fun getInstance() = RandomScreenFragment()
    }
}