package com.quantumman.randomgoals.app.ui.fragments

import android.os.Bundle
import android.view.View
import com.quantumman.randomgoals.R
import com.quantumman.randomgoals.app.ui.adapters.GoalItemsRecyclerAdapter
import com.quantumman.randomgoals.app.model.GoalItem
import com.quantumman.randomgoals.app.model.ParentWithListGoals
import com.quantumman.randomgoals.app.ui.presenters.EditParentListPresenter
import com.quantumman.randomgoals.app.ui.views.EditParentListsView
import com.quantumman.randomgoals.helpers.HandleSnackMessage
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import org.koin.android.ext.android.get
import timber.log.Timber

class EditParentListFragment : MvpAppCompatFragment(R.layout.edit_parent_list_layout), EditParentListsView {

    private var parentListArg: ParentWithListGoals? = null

    @InjectPresenter
    lateinit var allParentListsPresenter: EditParentListPresenter
    @ProvidePresenter
    fun provide(): EditParentListPresenter = get()

    private lateinit var goalItemsAdapter: GoalItemsRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        goalItemsAdapter = GoalItemsRecyclerAdapter()
        when (val arg = arguments) {
            null -> { Timber.e("Arg = null") }
            else -> {
                parentListArg = arg.getParcelable(ARG_KEY_PARENT)
            }
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        allParentListsPresenter.showData()
    }

    override fun initShowingData() {

    }

    override fun saveWholeParentList(parentWithListGoals: ParentWithListGoals) {
        TODO("Not yet implemented")
    }

    override fun editParentName(newName: String) {
        TODO("Not yet implemented")
    }

    override fun saveNewGoalItem(goal: GoalItem) {
        TODO("Not yet implemented")
    }

    override fun deleteGoalItem(goal: GoalItem) {
        TODO("Not yet implemented")
    }

    override fun deleteAllGoals() {
        TODO("Not yet implemented")
    }

    override fun showError(message: String) {
        (activity as? HandleSnackMessage)?.showSnack(message = message)
    }

    companion object {
        private val TAG = EditParentListFragment::class.simpleName
        private const val ARG_KEY_PARENT: String = "parent_edit"
        fun getEditParentInstance(parentList: ParentWithListGoals) = EditParentListFragment().apply {
            arguments = Bundle().apply { putParcelable(ARG_KEY_PARENT, parentList) }
        }

        fun getCreateParentInstance() = EditParentListFragment()
    }
}