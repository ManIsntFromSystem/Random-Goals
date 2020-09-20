package com.quantumman.randomgoals.app.ui.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.quantumman.randomgoals.R
import com.quantumman.randomgoals.app.ui.adapters.GoalItemsAdapter
import com.quantumman.randomgoals.app.ui.presenters.EditParentListPresenter
import com.quantumman.randomgoals.app.ui.views.EditParentListsView
import com.quantumman.randomgoals.helpers.HandleSnackMessage
import com.quantumman.randomgoals.helpers.enums.ParentListMode
import com.quantumman.randomgoals.helpers.extensions.toEditable
import kotlinx.android.synthetic.main.edit_parent_list_layout.*
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import org.koin.android.ext.android.get
import timber.log.Timber

class EditParentListFragment : MvpAppCompatFragment(R.layout.edit_parent_list_layout), EditParentListsView {

    private var parentListNameArg: String? = null

    @InjectPresenter
    lateinit var editPresenter: EditParentListPresenter
    @ProvidePresenter
    fun provide(): EditParentListPresenter = get()

    private lateinit var goalItemsAdapter: GoalItemsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        when (val arg = arguments) {
            null -> {
                activity?.title = getString(R.string.title_create_parent_list)
                editPresenter.startCreationFragment(ParentListMode.CREATION, getString(R.string.default_parent_list_name))
            }
            else -> {
                activity?.title = getString(R.string.title_edit_parent_list)
                parentListNameArg = arg.getString(ARG_KEY_PARENT)
                editPresenter.startCreationFragment(ParentListMode.EDIT, parentListNameArg ?:"")
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        if (parentListNameArg == null) {
//            textIETNameListGoals.apply {
//                text = getString(R.string.default_parent_list_name).toEditable()
//                setSelectAllOnFocus(true)
//                requestFocus()
//                selectAll()
//            }
//        }
        editPresenter.viewCreated()
    }

    ////////////////*    For Recycler   *//////////////////

    override fun startListeningData() {
        recyclerViewEditGoals.apply {
            layoutManager = LinearLayoutManager(get())
            goalItemsAdapter = GoalItemsAdapter()
            adapter = goalItemsAdapter
        }
        editPresenter.data.observe(viewLifecycleOwner, { parentList ->
            parentList?.let {
                textIETNameListGoals.text = it.parentName.toEditable()
                ivEditParentIcon.setImageResource(R.drawable.ic_purpose)
                if (it.listGoals.isNotEmpty()) {
                    editPresenter.changedStatusData(existence = true)
                    goalItemsAdapter.submitList(it.listGoals)
                } else editPresenter.changedStatusData(existence = false)
            }
        })
        //transferring the call to the presenter for handling
        goalItemsAdapter.onItemClick = editPresenter::handleClickToGoalItem
    }

    override fun changeStateRecycler(existence: Boolean) = if (existence) {
        ivHolderGoals.visibility = View.INVISIBLE
        recyclerViewEditGoals.visibility = View.VISIBLE
    } else {
        ivHolderGoals.visibility = View.VISIBLE
        recyclerViewEditGoals.visibility = View.INVISIBLE
    }

    ////////////*     Start observing     *///////////

    override fun initComponents() {
        textIETNameListGoals.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(before: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {}
        })

        ivEditParentIcon.setOnClickListener {

        }
        fabAddGoalToParentList.setOnClickListener {
            val newGoal = textIETNameNewGoal.text.toString().trim()
            editPresenter.saveNewGoalItem(name = newGoal)
            textIETNameNewGoal.text?.clear()
        }
    }

    /////////////////*   Menu   *////////////////////

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        inflater.inflate(R.menu.sort_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        return when (item.itemId) {
//            R.id.sort_by_name -> {
//                actorAdapter.swap(repository.actorsSortedByName)
//                true
//            }
//            R.id.sort_by_rating -> {
//                actorAdapter.swap(repository.actorsSortedByRating)
//                true
//            }
//            R.id.sort_by_birth -> {
//                actorAdapter.swap(repository.actorsSortedByYearOfBirth)
//                true
//            }
//            else ->
                return super.onOptionsItemSelected(item)
    }

    /////////////////*   Errors   *//////////////////

    override fun showError(message: String) {
        (activity as? HandleSnackMessage)?.showMessage(message = message)
    }

    override fun showError(message: Int) {
        (activity as? HandleSnackMessage)?.showMessage(message = getString(message))
    }

    //////////////*     Override Lifecycle methods  *//////////////

    override fun onDestroy() {
        editPresenter.destroyView()
        super.onDestroy()
    }

    //////////////*     Companion  */////////////////

    companion object {
        private val TAG = EditParentListFragment::class.simpleName
        private const val ARG_KEY_PARENT: String = "parent_edit"
        fun getEditParentInstance(parentName: String) = EditParentListFragment().apply {
            arguments = Bundle().apply { putString(ARG_KEY_PARENT, parentName) }
        }

        fun getCreateParentInstance() = EditParentListFragment()
    }
}