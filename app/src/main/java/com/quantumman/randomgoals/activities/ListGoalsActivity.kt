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
    private lateinit var listNamesGoals: List<String>
    private lateinit var listAllGoals: List<Goal>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_goals)

        addNewGoalFltActBtn = findViewById(R.id.addNewGoalFltActBtn)
        recyclerViewEditGoals = findViewById(R.id.recyclerViewListGoals)
        goalsContent = GoalsContentProvider()
        listAllGoals = GoalDBOpenHelper(this).getAllItemsListGoals()
        listNamesGoals = listAllGoals.map { it.nameListGoals }.distinct()
    }

    fun addNewListGoalsFloatBtn(view: View) {
        val intent = Intent(this, EditItemsListActivity::class.java)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        initRecycler()
        Log.d("MyLog", "On Resume")
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
                val listIdForDel = mutableListOf<Int>()
                listIdForDel.addAll(listAllGoals
                    .filter { it.nameListGoals == listNamesGoals[viewHolder.adapterPosition] }
                    .map { it.id })

                val rowsDeleted = goalsContent.delete(
                    CONTENT_URI,
                    "$COLUMN_NAME_LIST=?",
                    arrayOf(listNamesGoals[viewHolder.adapterPosition])
                )

                if (rowsDeleted == 0)
                    Snackbar.make(activity_list_goals, "Del not successful", Snackbar.LENGTH_LONG)
                        .show()
                else
                    Snackbar.make(activity_list_goals, "Del successful", Snackbar.LENGTH_LONG)
                        .show()
            }
        }

    private fun deleteGoal(currentProductUri: Uri) {

    }

    override fun onGoalClick(position: Int) {
        val intent = Intent(this, EditItemsListActivity::class.java)
        intent.putExtra("list_name", listNamesGoals[position])
        startActivity(intent)
//        val uri: Uri =  ContentUris.withAppendedId(CONTENT_URI, position.toLong())
//        deleteGoal(uri)
    }


}
