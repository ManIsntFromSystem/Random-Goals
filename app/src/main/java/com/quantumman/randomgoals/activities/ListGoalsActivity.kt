package com.quantumman.randomgoals.activities

import android.content.ContentUris
import android.content.Intent
import android.icu.lang.UCharacter
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.quantumman.randomgoals.R
import com.quantumman.randomgoals.data.GoalsContentProvider
import com.quantumman.randomgoals.data.GoalsListAdapter
import com.quantumman.randomgoals.data.ItemsGoalAdapters
import com.quantumman.randomgoals.data.GoalsContract.MemberEntry.CONTENT_URI
import com.quantumman.randomgoals.util.VerticalSpacingItemDecorator
import kotlinx.android.synthetic.main.activity_list_goals.*

class ListGoalsActivity : AppCompatActivity(), GoalsListAdapter.OnGoalListener {
    private lateinit var recyclerViewEditGoals: RecyclerView
    private lateinit var addNewGoalFltActBtn: FloatingActionButton
    private lateinit var goalsList: List<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_goals)

        addNewGoalFltActBtn = findViewById(R.id.addNewGoalFltActBtn)
        recyclerViewEditGoals = findViewById(R.id.recyclerViewListGoals)
        goalsList = getListNamesGoals()
        initRecycler()
    }

    fun addNewListGoalsFloatBtn(view: View) {
        val intent = Intent(this, EditItemsListActivity::class.java)
        startActivity(intent)
    }

    private fun getListNamesGoals(): List<String> {
        val listResult = mutableListOf<String>()
        GoalsContentProvider().getAllItemsListGoals(this).forEach {
            listResult += it.nameListGoals
        }
        return listResult.toSet().toList()
    }

    private fun initRecycler() { //add delete button and its Listener
        val mAdapter = GoalsListAdapter(this, goalsList, this)
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
                val uri: Uri = ContentUris.withAppendedId(
                    CONTENT_URI,
                    viewHolder.adapterPosition.toLong()
                )
                deleteGoal(uri)
            }
        }

    private fun deleteGoal(currentProductUri: Uri) {
        val rowsDeleted = GoalsContentProvider().delete(
            currentProductUri,
            null, null
        )
        if (rowsDeleted == 0)
            Snackbar.make(activity_list_goals, "Del not successful", Snackbar.LENGTH_LONG).show()
        else
            Snackbar.make(activity_list_goals,"Del successful", Snackbar.LENGTH_LONG).show()
    }

    override fun onGoalClick(position: Int) {
        val intent = Intent(this, EditItemsListActivity::class.java)
        intent.putExtra("list_name", goalsList[position])
        startActivity(intent)

//        val uri: Uri =  ContentUris.withAppendedId(CONTENT_URI, position.toLong())
//        deleteGoal(uri)
    }
}
