package com.quantumman.randomgoals.app.views

import android.content.Intent
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
import com.quantumman.randomgoals.data.*
import com.quantumman.randomgoals.data.helpers.GoalsContract.GoalsListEntry.COLUMN_ICON_GOAL
import com.quantumman.randomgoals.data.helpers.GoalsContract.GoalsListEntry.COLUMN_NAME_LIST
import com.quantumman.randomgoals.data.helpers.GoalsContract.GoalsListEntry.CONTENT_URI_LIST
import com.quantumman.randomgoals.data.helpers.GoalsContract.GoalsListEntry.ID_LIST
import com.quantumman.randomgoals.data.helpers.GoalsContract.ItemGoalEntry.COLUMN_ID_LIST
import com.quantumman.randomgoals.data.helpers.GoalsContract.ItemGoalEntry.CONTENT_URI_GOAL
import com.quantumman.randomgoals.app.model.ListNames
import com.quantumman.randomgoals.data.helpers.GoalDBOpenHelper
import com.quantumman.randomgoals.util.VerticalSpacingItemDecorator
import kotlinx.android.synthetic.main.activity_list_goals.*

class ListGoalsActivity : AppCompatActivity(), GoalsListAdapter.OnGoalListener {
    private lateinit var recyclerViewEditGoals: RecyclerView
    private lateinit var addNewGoalFltActBtn: FloatingActionButton
    private lateinit var goalsContent: GoalsContentProvider
    private lateinit var goalsDBHelper: GoalDBOpenHelper
    private lateinit var listNamesGoals: MutableList<ListNames>
    private lateinit var listAllNames: List<String>
    private var point: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_goals)

        addNewGoalFltActBtn = findViewById(R.id.addNewGoalFltActBtn)
        recyclerViewEditGoals = findViewById(R.id.recyclerViewListGoals)
        goalsContent = GoalsContentProvider()
        goalsDBHelper = GoalDBOpenHelper(this)
        listNamesGoals = mutableListOf()
        initLists()
        initRecycler()
    }
    private fun initRecycler() {
        val mAdapter = GoalsListAdapter(this, listAllNames, this)
        recyclerViewEditGoals.apply {
            layoutManager = LinearLayoutManager(context)
            hasFixedSize()
            addItemDecoration(VerticalSpacingItemDecorator(10))
            ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerViewEditGoals)
            itemAnimator = DefaultItemAnimator()
            adapter = mAdapter
        }
    }

    fun addNewListGoalsFloatBtn(view: View) {
        val intent = Intent(this, EditItemsListActivity::class.java)
        startActivity(intent)
    }

    private fun initLists() {
        if (this::listNamesGoals.isInitialized && listNamesGoals.isNotEmpty())
            listNamesGoals.clear()

        val cursor =  contentResolver.query(CONTENT_URI_LIST, null,
            null, null,null, null)
        if (cursor != null) {
            while (cursor.moveToNext()) {
                val itemListId = cursor.getInt(cursor.getColumnIndex(ID_LIST))
                val itemListName = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_LIST))
                val itemListIcon = cursor.getString(cursor.getColumnIndex(COLUMN_ICON_GOAL))
                listNamesGoals.plusAssign(
                    ListNames(
                        itemListId,
                        itemListName,
                        itemListIcon
                    )
                )
            }
            cursor.close()
        }
        listAllNames = listNamesGoals.map { it.nameList }
    }

    override fun onResume() {
        super.onResume()
        if (point > 0) {
            initLists()
            initRecycler()
        }
        point++
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
                deleteGoal(listNamesGoals[viewHolder.adapterPosition].id)
                initLists()
                initRecycler()
            }
        }

    private fun deleteGoal(idList: Int) {
        val rowsDeletedList = contentResolver
            .delete(CONTENT_URI_LIST, "$ID_LIST=?", arrayOf(idList.toString()))
        val rowsDeletedGoals = contentResolver
            .delete(CONTENT_URI_GOAL, "$COLUMN_ID_LIST=?", arrayOf(idList.toString()))
        if (rowsDeletedList == 0 || rowsDeletedGoals == 0)
            Snackbar.make(act_list_goals, "Del not successful", Snackbar.LENGTH_LONG).show()
        else
            Snackbar.make(act_list_goals, "Del successful", Snackbar.LENGTH_LONG).show()
    }

    override fun onGoalClick(position: Int) {
        val intent = Intent(this, EditItemsListActivity::class.java)
        intent.putExtra("list_name", listAllNames[position])
        intent.putExtra("list_id", position + 1)
        startActivity(intent)
    }
}