package com.quantumman.randomgoals.app.views

import android.content.ContentValues
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NavUtils
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.quantumman.randomgoals.R
import com.quantumman.randomgoals.data.helpers.GoalDBOpenHelper
import com.quantumman.randomgoals.data.GoalsContentProvider
import com.quantumman.randomgoals.data.helpers.GoalsContract.GoalsListEntry.COLUMN_ICON_GOAL
import com.quantumman.randomgoals.data.helpers.GoalsContract.GoalsListEntry.COLUMN_NAME_LIST
import com.quantumman.randomgoals.data.helpers.GoalsContract.GoalsListEntry.CONTENT_URI_LIST
import com.quantumman.randomgoals.data.helpers.GoalsContract.ItemGoalEntry.COLUMN_ID_LIST
import com.quantumman.randomgoals.data.helpers.GoalsContract.ItemGoalEntry.COLUMN_NAME_GOAL
import com.quantumman.randomgoals.data.helpers.GoalsContract.ItemGoalEntry.CONTENT_URI_GOAL
import com.quantumman.randomgoals.data.ItemsGoalAdapters
import com.quantumman.randomgoals.app.model.Goal
import com.quantumman.randomgoals.app.model.ListNames
import com.quantumman.randomgoals.util.toEditable

class EditItemsListActivity : AppCompatActivity() {
    private lateinit var etNameListGoals: EditText
    private lateinit var nameNewItemEditText: EditText
    private lateinit var iconNewItemImageView: ImageView
    private lateinit var addNewItemToListImgBtn: FloatingActionButton
    private lateinit var recyclerViewEditGoals: RecyclerView
    private lateinit var goalDBHelper: GoalDBOpenHelper
    private lateinit var itemGoalsAdapter: ItemsGoalAdapters
    private lateinit var myItemSaveMenu: MenuItem
    private lateinit var allListGoals: List<Goal>
    private lateinit var changedListNameInET: String
    private var beforeChangedListNameInET = ""
    private var currentListName: ListNames? = null
    private var intentListName: String? = null
    private var intentListId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_items_list)

        etNameListGoals = findViewById(R.id.etNameListGoals)
        nameNewItemEditText = findViewById(R.id.nameNewItemEditText)
        iconNewItemImageView = findViewById(R.id.iconNewItemImageView)
        addNewItemToListImgBtn = findViewById(R.id.addNewItemToListImgBtn)
        recyclerViewEditGoals = findViewById(R.id.recyclerViewEditGoals)
        goalDBHelper = GoalDBOpenHelper(this)
        allListGoals = mutableListOf()
        initData()
        itemGoalsAdapter = ItemsGoalAdapters(this, allListGoals.toTypedArray())
        createRecycler()
        addListNameETListener()
    }

    private fun addListNameETListener() =
        etNameListGoals.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {}
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            changedListNameInET = s.toString()
            myItemSaveMenu.isVisible = changedListNameInET != beforeChangedListNameInET
        }
    })

    private fun initData() {
        intentListName = intent.getStringExtra("list_name")
        intentListId = intent.getIntExtra("list_id", -1)
        if (intentListName != null) {
            etNameListGoals.text = intentListName?.toEditable()
            intentListName?.let { updateCurrentListName(it) }
            initAllGoalsByName()
        }
    }

    private fun updateCurrentListName(name: String) {
        beforeChangedListNameInET = name
        currentListName = goalDBHelper.queryLists(null, name).first()
    }

    private fun initAllGoalsByName() {
        if (this::allListGoals.isInitialized && allListGoals.isNotEmpty())
            allListGoals.toMutableList().clear()
        currentListName?.id?.let {
            allListGoals = goalDBHelper.queryGoals(it.toString())
        }
    }

    private fun createRecycler() = recyclerViewEditGoals.apply {
        layoutManager = LinearLayoutManager(context)
        hasFixedSize()
        itemAnimator = DefaultItemAnimator()
        adapter = itemGoalsAdapter
    }

    fun addNewItemGoalToDB(view: View) {
        val nameGoalList = etNameListGoals.text.toString().trim()
        val nameGoal = nameNewItemEditText.text.toString().trim()
        val rootView: View = window.decorView.findViewById(android.R.id.content)
        when {
            nameGoalList.isEmpty() ->
                Snackbar.make(rootView, "Input list name", Snackbar.LENGTH_SHORT).show()
            nameGoal.isEmpty() ->
                Snackbar.make(rootView, "Input goal name", Snackbar.LENGTH_SHORT).show()
            else -> {
                saveDataToDB(nameGoal, nameGoalList)
                initAllGoalsByName()
                itemGoalsAdapter.updateData(allListGoals.toTypedArray())
            }
        }
    }

    private fun saveDataToDB(nameGoal: String?, nameGoalList: String?) {
        if (intentListName.isNullOrBlank() && nameGoalList != null) {
            ContentValues().apply {
                put(COLUMN_NAME_LIST, nameGoalList)
                put(COLUMN_ICON_GOAL, iconNewItemImageView.tag.toString())
                val uri = contentResolver.insert(CONTENT_URI_LIST, this)
                if (uri == null) Log.d("MyLog", "Insertion of data in the table failed")
                else Log.d("MyLog", "Insertion of data is successful")
            }
            updateCurrentListName(nameGoalList)
        }
        if (nameGoal != null) {
            ContentValues().apply {
                put(COLUMN_NAME_GOAL, nameGoal)
                put(COLUMN_ID_LIST, currentListName?.id)
                val uri = contentResolver.insert(CONTENT_URI_GOAL, this)
                if (uri == null) Log.d("MyLog", "Insertion of data in the table failed")
                else Log.d("MyLog", "Insertion of data is successful")
            }
            nameNewItemEditText.text.clear()
        }
    }

    private fun updateListName() {
        ContentValues().apply {
            put(COLUMN_NAME_LIST, changedListNameInET)
            val uri = Uri.withAppendedPath(CONTENT_URI_LIST, currentListName!!.id.toString())
            val up = contentResolver.update(uri, this, null, null)
            if (up == 0) Log.d("MyLog", "Updated of data in table failed")
            else Log.d("MyLog", "Updating is successfully")
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_edit_goal_items, menu)
        if (menu != null) {
            myItemSaveMenu = menu.findItem(R.id.save_list_goals)
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.save_list_goals -> {
                if (currentListName != null) updateListName()
                else saveDataToDB(changedListNameInET, null)
                myItemSaveMenu.isVisible = false
                return true
            }
            android.R.id.home -> {
                NavUtils.navigateUpFromSameTask(this)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (currentListName != null && allListGoals.isEmpty()) {
            val uri = Uri.withAppendedPath(CONTENT_URI_LIST, currentListName?.id.toString())
            goalDBHelper.delListGoalsByListName(uri, null, null)
        }
    }

    fun ivChooseIconForListGoals(view: View) {
        val myDialogIcons = ChooseIconDialogFragment()
        val manager = supportFragmentManager
        myDialogIcons.show(manager, "DialogIcons")
    }
}
