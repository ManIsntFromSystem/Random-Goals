package com.quantumman.randomgoals.app.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.quantumman.randomgoals.R
import com.quantumman.randomgoals.app.ui.adapters.ParentListsRecyclerAdapter
import com.quantumman.randomgoals.app.ui.presenters.AllParentListsPresenter
import com.quantumman.randomgoals.app.ui.views.AllParentListsView
import com.quantumman.randomgoals.helpers.HandleSnackMessage
import kotlinx.android.synthetic.main.all_parent_lists_fragment.*
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import org.koin.android.ext.android.get

class AllParentListsFragment : MvpAppCompatFragment(R.layout.all_parent_lists_fragment), AllParentListsView {

    @InjectPresenter
    lateinit var allParentListsPresenter: AllParentListsPresenter
    @ProvidePresenter
    fun provide(): AllParentListsPresenter = get()

    private lateinit var parentRecyclerAdapter: ParentListsRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parentRecyclerAdapter = ParentListsRecyclerAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        allParentListsPresenter.showData()
    }

    override fun initShowingData() {
        recyclerViewParentLists.apply {
            layoutManager = LinearLayoutManager(activity)
            itemAnimator = DefaultItemAnimator()
            adapter = parentRecyclerAdapter
        }
        allParentListsPresenter.data.observe(viewLifecycleOwner, {
            parentRecyclerAdapter.updateData(it)
        })
    }

    override fun deleteParentList() {
        TODO("Not yet implemented")
    }

    override fun showError(message: String) {
        (activity as? HandleSnackMessage)?.showSnack(message = message)
    }

    companion object {
        private val TAG = AllParentListsFragment::class.simpleName
        fun getInstance() = AllParentListsFragment()
    }
}