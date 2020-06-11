package com.quantumman.randomgoals.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.quantumman.randomgoals.R
import com.quantumman.randomgoals.data.*
import com.quantumman.randomgoals.data.GoalsContract.MemberEntry.COLUMN_NAME_LIST
import com.quantumman.randomgoals.data.GoalsContract.MemberEntry.CONTENT_URI
import com.quantumman.randomgoals.model.Goal
import com.quantumman.randomgoals.util.VerticalSpacingItemDecorator
import kotlinx.android.synthetic.main.activity_list_goals.*

class ListGoalsActivity : AppCompatActivity(), GoalsListAdapter.OnGoalListener {
    private lateinit var recyclerViewEditGoals: RecyclerView
    private lateinit var addNewGoalFltActBtn: FloatingActionButton
    private lateinit var goalsContent: GoalsContentProvider
    private lateinit var goalsDBHelper: GoalDBOpenHelper
    private lateinit var listNamesGoals: List<String>
    private lateinit var listAllGoals: List<Goal>
    private var point: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_goals)

        addNewGoalFltActBtn = findViewById(R.id.addNewGoalFltActBtn)
        recyclerViewEditGoals = findViewById(R.id.recyclerViewListGoals)
        goalsContent = GoalsContentProvider()
        goalsDBHelper = GoalDBOpenHelper(this)
        initLists()
        initRecycler()
    }

    fun addNewListGoalsFloatBtn(view: View) {
        val intent = Intent(this, EditItemsListActivity::class.java)
        startActivity(intent)
    }

    private fun initLists() {
        if (this::listAllGoals.isInitialized && this::listNamesGoals.isInitialized &&
            listAllGoals.isNotEmpty() && listNamesGoals.isNotEmpty()) {
            listNamesGoals.toMutableList().clear()
            listAllGoals.toMutableList().clear()
        }
        listAllGoals = goalsDBHelper.queryGoals(null)
        listNamesGoals = listAllGoals.map { it.nameListGoals }.distinct().toMutableList()
    }

    override fun onResume() {
        super.onResume()
        if (point > 0) {
            initLists()
            initRecycler()
        }
        point++
    }

    private fun initRecycler() { //add delete button and its Listener
        val mAdapter = GoalsListAdapter(this, listNamesGoals, this)
        recyclerViewEditGoals.apply {
            layoutManager = LinearLayoutManager(context)
            hasFixedSize()
            addItemDecoration(VerticalSpacingItemDecorator(10))
            ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerViewEditGoals)
            itemAnimator = DefaultItemAnimator()
            adapter = mAdapter
        }
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
                deleteGoal(listNamesGoals[viewHolder.adapterPosition])
                initLists()
                initRecycler()
            }
        }

    private fun deleteGoal(listName: String) {
        val rowsDeleted = goalsDBHelper
            .delListGoalsByListName(CONTENT_URI, "$COLUMN_NAME_LIST=?", arrayOf(listName))
        if (rowsDeleted == 0)
            Snackbar.make(activity_list_goals, "Del not successful", Snackbar.LENGTH_LONG)
                .show()
        else
            Snackbar.make(activity_list_goals, "Del successful", Snackbar.LENGTH_LONG)
                .show()
    }

    override fun onGoalClick(position: Int) {
        val intent = Intent(this, EditItemsListActivity::class.java)
        intent.putExtra("list_name", listNamesGoals[position])
        startActivity(intent)
    }
}