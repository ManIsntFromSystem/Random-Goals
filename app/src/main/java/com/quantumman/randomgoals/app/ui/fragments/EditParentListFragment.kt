package com.quantumman.randomgoals.app.ui.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.app.NavUtils
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.jakewharton.rxbinding4.view.clicks
import com.jakewharton.rxbinding4.widget.editorActionEvents
import com.quantumman.randomgoals.R
import com.quantumman.randomgoals.app.ui.adapters.GoalItemsRecyclerAdapter
import com.quantumman.randomgoals.app.model.GoalItem
import com.quantumman.randomgoals.app.model.ParentWithListGoals
import com.quantumman.randomgoals.app.ui.presenters.EditParentListPresenter
import com.quantumman.randomgoals.app.ui.views.EditParentListsView
import com.quantumman.randomgoals.helpers.HandleSnackMessage
import com.quantumman.randomgoals.helpers.extensions.toEditable
import kotlinx.android.synthetic.main.edit_parent_list_layout.*
import kotlinx.android.synthetic.main.item_for_dialog_choose_icon.*
import kotlinx.android.synthetic.main.item_list_goals.*
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import org.koin.android.ext.android.get
import timber.log.Timber
import java.util.concurrent.TimeUnit

class EditParentListFragment : MvpAppCompatFragment(R.layout.edit_parent_list_layout), EditParentListsView {

    private var parentListArg: ParentWithListGoals? = null

    @InjectPresenter
    lateinit var editPresenter: EditParentListPresenter
    @ProvidePresenter
    fun provide(): EditParentListPresenter = get()

    private lateinit var goalItemsAdapter: GoalItemsRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        goalItemsAdapter = GoalItemsRecyclerAdapter()
        when (val arg = arguments) {
            null -> {
                textIETNameListGoals.text = getString(R.string.defult_parent_list_name).toEditable()
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    textIETNameListGoals.focusable
                }
                Timber.e("Arg = null")
            }
            else -> {
                parentListArg = arg.getParcelable(ARG_KEY_PARENT)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerViewEditGoals.apply {
            layoutManager = LinearLayoutManager(get())
            adapter = goalItemsAdapter
        }
        editPresenter.showData()
    }

    override fun startListeningData() {
        var parent: ParentWithListGoals? = null
        editPresenter.data.observe(this, { parentList ->
            parentList?.let {
                textIETNameListGoals.text = it.parentName.toEditable()
                ivEditParentIcon.setImageResource(R.drawable.ic_purpose)
                if (it.listGoals.isNotEmpty()) {
                    editPresenter.statusData(existence = true)
                    goalItemsAdapter.updateData(it.listGoals)
                } else editPresenter.statusData(existence = false)
            }
        })
    }

    override fun changeStateRecycler(existence: Boolean) = if (existence) {
        ivHolderGoals.visibility = View.INVISIBLE
        recyclerViewEditGoals.visibility = View.VISIBLE
    } else {
        ivHolderGoals.visibility = View.VISIBLE
        recyclerViewEditGoals.visibility = View.INVISIBLE
    }

    override fun initComponents() {
        textIETNameListGoals.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(before: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {}
        })

        ivEditParentIcon.clicks().throttleLatest(500, TimeUnit.MILLISECONDS)
            .subscribe( {

            }, {

            })
        fabAddGoalToParentList.clicks().throttleLatest(500, TimeUnit.MILLISECONDS)
            .subscribe( {
                val name = textIETNameNewGoal.text.toString().trim()
                editPresenter.saveNewGoalItem(name = name)
            }, {

            })
    }

    override fun showError(message: String) {
        (activity as? HandleSnackMessage)?.showSnack(message = message)
    }

    override fun showError(message: Int) {
        (activity as? HandleSnackMessage)?.showSnack(message = getString(message))
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