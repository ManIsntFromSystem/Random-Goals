package com.quantumman.randomgoals.app.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.quantumman.randomgoals.R
import com.quantumman.randomgoals.app.ui.adapters.ParentListsRecyclerAdapter
import com.quantumman.randomgoals.app.ui.presenters.AllParentListsPresenter
import com.quantumman.randomgoals.app.ui.views.AllParentListsView
import com.quantumman.randomgoals.helpers.HandleSnackMessage
import com.quantumman.randomgoals.utils.SwipeItemHelper
import com.quantumman.randomgoals.utils.SwipeListener
import kotlinx.android.synthetic.main.all_parent_lists_layout.*
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import org.koin.android.ext.android.get

class AllParentListsFragment : MvpAppCompatFragment(R.layout.all_parent_lists_layout), AllParentListsView {

    @InjectPresenter lateinit var allParentListsPresenter: AllParentListsPresenter
    @ProvidePresenter fun provide(): AllParentListsPresenter = get()

    private val parentRecyclerAdapter = ParentListsRecyclerAdapter()
    private val swipeItemHelper: SwipeItemHelper by lazy {
        SwipeItemHelper(requireContext(), SwipeListener(parentRecyclerAdapter))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        allParentListsPresenter.viewCreated()
    }

    override fun initComponents() {
        allParentListsPresenter.data.observe(viewLifecycleOwner, {
            parentRecyclerAdapter.submitList(it)
        })
        //transferring the call to the presenter for handling
        parentRecyclerAdapter.onItemClick = allParentListsPresenter::handleClickToParentItem

        fabAddNewParentWithGoals.setOnClickListener {
            allParentListsPresenter.handleClickToAddNewListGoals()
        }
    }

    override fun changeStateRecycler(existence: Boolean) = if (existence) {
        ivHolderParentLists.visibility = View.INVISIBLE
        recyclerViewParentLists.visibility = View.VISIBLE
    } else {
        ivHolderParentLists.visibility = View.VISIBLE
        recyclerViewParentLists.visibility = View.INVISIBLE
    }

    override fun showError(message: String) {
        (activity as? HandleSnackMessage)?.showMessage(message = message)
    }

    override fun showError(message: Int) {
        (activity as? HandleSnackMessage)?.showMessage(message = getString(message))
    }

    override fun onStart() {
        super.onStart()
        swipeItemHelper.attachToRecyclerView(
            recyclerViewParentLists.apply {
                layoutManager = LinearLayoutManager(activity)
                adapter = parentRecyclerAdapter
            }
        )
    }

    override fun onStop() {
        swipeItemHelper.detachToRecyclerView()
        recyclerViewParentLists.adapter = null
        super.onStop()
    }

    companion object {
        private val TAG = AllParentListsFragment::class.simpleName
        fun getInstance() = AllParentListsFragment()
    }
}