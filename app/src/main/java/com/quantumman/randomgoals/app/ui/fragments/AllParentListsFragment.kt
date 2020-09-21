package com.quantumman.randomgoals.app.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.quantumman.randomgoals.R
import com.quantumman.randomgoals.app.ui.adapters.ParentListsRecyclerAdapter
import com.quantumman.randomgoals.app.ui.presenters.AllParentListsPresenter
import com.quantumman.randomgoals.app.ui.views.AllParentListsView
import com.quantumman.randomgoals.helpers.HandleSnackMessage
import kotlinx.android.synthetic.main.all_parent_lists_fragment.*
import kotlinx.android.synthetic.main.edit_parent_list_layout.*
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
        allParentListsPresenter.viewCreated()
    }

    override fun initComponents() {
        recyclerViewParentLists.apply {
            layoutManager = LinearLayoutManager(activity)
            itemAnimator = DefaultItemAnimator()
            adapter = parentRecyclerAdapter
        }
        allParentListsPresenter.data.observe(viewLifecycleOwner, {
            parentRecyclerAdapter.submitList(it)
        })

        //transferring the call to the presenter for handling
        parentRecyclerAdapter.onItemClick = allParentListsPresenter::handleClickToParentItem

        fabAddNewParentWithGoals.setOnClickListener {
            val fragment = EditParentListFragment.getCreateParentInstance()
        }
    }

    override fun changeStateRecycler(existence: Boolean) = if (existence) {
//        ivHolderGoals.visibility = View.INVISIBLE
//        recyclerViewEditGoals.visibility = View.VISIBLE
    } else {
//        ivHolderGoals.visibility = View.VISIBLE
//        recyclerViewEditGoals.visibility = View.INVISIBLE
    }

    private var itemTouchHelperCallback: ItemTouchHelper.SimpleCallback =
        object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

            }
        }

    /////////////////*   Errors   *//////////////////

    override fun showError(message: String) {
        (activity as? HandleSnackMessage)?.showMessage(message = message)
    }

    override fun showError(message: Int) {
        (activity as? HandleSnackMessage)?.showMessage(message = getString(message))
    }

    //////////////*     Override Lifecycle methods  *//////////////


    ///////////////////////**********///////////////////////////

    companion object {
        private val TAG = AllParentListsFragment::class.simpleName
        fun getInstance() = AllParentListsFragment()
    }
}