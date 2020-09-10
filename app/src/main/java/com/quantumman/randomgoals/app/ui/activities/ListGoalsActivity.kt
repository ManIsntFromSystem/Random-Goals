package com.quantumman.randomgoals.app.ui.activities

//import android.content.Intent
//import android.os.Bundle
//import android.view.View
//import androidx.appcompat.app.AppCompatActivity
//import androidx.recyclerview.widget.DefaultItemAnimator
//import androidx.recyclerview.widget.ItemTouchHelper
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import com.google.android.material.floatingactionbutton.FloatingActionButton
//import com.google.android.material.snackbar.Snackbar
//import com.quantumman.randomgoals.R
//import com.quantumman.randomgoals.helpers.contracts.GoalsContract.GoalsListEntry.CONTENT_URI_LIST
//import com.quantumman.randomgoals.helpers.contracts.GoalsContract.GoalsListEntry.ID_LIST
//import com.quantumman.randomgoals.helpers.contracts.GoalsContract.ItemGoalEntry.COLUMN_ID_LIST
//import com.quantumman.randomgoals.helpers.contracts.GoalsContract.ItemGoalEntry.CONTENT_URI_GOAL
//import com.quantumman.randomgoals.app.model.ParentListGoals
//import com.quantumman.randomgoals.app.ui.adapters.ParentListsRecyclerAdapter
//import com.quantumman.randomgoals.data.db.GoalDBOpenHelper
//import com.quantumman.randomgoals.data.providers.GoalsContentProvider
//import com.quantumman.randomgoals.utils.VerticalSpacingItemDecorator
//
//class ListGoalsActivity : AppCompatActivity()/*, ParentListsRecyclerAdapter.OnParentItemListener*/ {
//    private lateinit var recyclerViewEditGoals: RecyclerView
//    private lateinit var addNewGoalFltActBtn: FloatingActionButton
//    private lateinit var goalsContent: GoalsContentProvider
//    private lateinit var goalsDBHelper: GoalDBOpenHelper
//    private lateinit var goalListNamesParents: List<ParentListGoals>
//    private var point: Int = 0
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_list_goals)
//
//        addNewGoalFltActBtn = findViewById(R.id.addNewGoalFltActBtn)
//        recyclerViewEditGoals = findViewById(R.id.recyclerViewParentLists)
//        goalsContent = GoalsContentProvider()
//        goalsDBHelper = GoalDBOpenHelper(this)
//        initLists()
//        initRecycler()
//    }
//
//    private fun initLists() {
//        if (this::goalListNamesParents.isInitialized && goalListNamesParents.isNotEmpty())
//            goalListNamesParents.toMutableList().clear()
//        goalListNamesParents = goalsDBHelper.queryLists(null, null)
//    }
//
//    private fun initRecycler() =
//        recyclerViewEditGoals.apply {
//            layoutManager = LinearLayoutManager(context)
//            hasFixedSize()
//            addItemDecoration(VerticalSpacingItemDecorator(10))
//            ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerViewEditGoals)
//            itemAnimator = DefaultItemAnimator()
////            adapter = ParentListsRecyclerAdapter(this@ListGoalsActivity,
////                goalListNamesParents,this@ListGoalsActivity)
//        }
//
//    fun addNewListGoalsFloatBtn(view: View) {
//        startActivity(Intent(this, EditItemsListActivity::class.java))
//    }
//
//    private var itemTouchHelperCallback: ItemTouchHelper.SimpleCallback =
//        object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
//            override fun onMove(
//                recyclerView: RecyclerView,
//                viewHolder: RecyclerView.ViewHolder,
//                target: RecyclerView.ViewHolder
//            ): Boolean {
//                return false
//            }
//
//            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
//                deleteGoal(goalListNamesParents[viewHolder.adapterPosition].id)
//                initLists()
//                initRecycler()
//            }
//        }
//
//    private fun deleteGoal(idList: Int) {
//        val rowsDeletedList = contentResolver
//            .delete(CONTENT_URI_LIST, "$ID_LIST=?", arrayOf(idList.toString()))
//        val rowsDeletedGoals = contentResolver
//            .delete(CONTENT_URI_GOAL, "$COLUMN_ID_LIST=?", arrayOf(idList.toString()))
//        if (rowsDeletedList == 0 || rowsDeletedGoals == 0)
//            Snackbar.make(act_list_goals, "Del not successful", Snackbar.LENGTH_LONG).show()
//        else
//            Snackbar.make(act_list_goals, "Del successful", Snackbar.LENGTH_LONG).show()
//    }
//
////    override fun onGoalClick(position: Int) {
////        val intent = Intent(this, EditItemsListActivity::class.java)
////        intent.putExtra("currentObjListName", goalListNamesParents[position])
////        startActivity(intent)
////    }
//
//    override fun onResume() {
//        super.onResume()
//        if (point > 0) {
//            println("OnResume works")
//            initLists()
//            initRecycler()
//        }
//        point++
//    }
//}

/*Content query
//        val cursor =  contentResolver.query(CONTENT_URI_LIST, null,
//            null, null,null, null)
//        if (cursor != null) {
//            while (cursor.moveToNext()) {
//                val itemListId = cursor.getInt(cursor.getColumnIndex(ID_LIST))
//                val itemListName = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_LIST))
//                val itemListIcon = cursor.getInt(cursor.getColumnIndex(COLUMN_ICON_GOAL))
//                listNamesGoals.plusAssign(
//                    ListNames(
//                        itemListId,
//                        itemListName,
//                        itemListIcon
//                    )
//                )
//            }
//            cursor.close()
//        }
* */