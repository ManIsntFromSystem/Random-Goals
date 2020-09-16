package com.quantumman.randomgoals.app.ui.activities

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NavUtils
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.quantumman.randomgoals.R
import com.quantumman.randomgoals.app.model.Goal
import com.quantumman.randomgoals.app.model.ParentListGoals
import com.quantumman.randomgoals.app.ui.adapters.GoalItemsRecyclerAdapter
import com.quantumman.randomgoals.data.db.GoalDBOpenHelper
import com.quantumman.randomgoals.helpers.contracts.GoalsContract.GoalsListEntry.COLUMN_ICON_GOAL
import com.quantumman.randomgoals.helpers.contracts.GoalsContract.GoalsListEntry.COLUMN_NAME_LIST
import com.quantumman.randomgoals.helpers.contracts.GoalsContract.GoalsListEntry.CONTENT_URI_LIST
import com.quantumman.randomgoals.helpers.contracts.GoalsContract.ItemGoalEntry.COLUMN_ID_LIST
import com.quantumman.randomgoals.helpers.contracts.GoalsContract.ItemGoalEntry.COLUMN_NAME_GOAL
import com.quantumman.randomgoals.helpers.contracts.GoalsContract.ItemGoalEntry.CONTENT_URI_GOAL
import com.quantumman.randomgoals.helpers.extensions.toEditable

class EditItemsListActivity : AppCompatActivity(), GoalItemsRecyclerAdapter.OnDeleteGoalListener {
    private lateinit var addNewItemToListImgBtn: FloatingActionButton
    private lateinit var recyclerViewEditGoals: RecyclerView
    private lateinit var ivCurrentIconList: ImageView
    private lateinit var textIETNameListGoals: TextInputEditText
    private lateinit var textIETNameNewGoal: TextInputEditText
    private lateinit var itemsGoalsAdapterRecycler: GoalItemsRecyclerAdapter
    private lateinit var goalDBHelper: GoalDBOpenHelper
    private lateinit var intentCurrentParentListName: ParentListGoals
    private var changedIcon: Int? = null
    private var currentParentListName: ParentListGoals? = null
    private lateinit var changedListNameInET: String
    private lateinit var myItemSaveMenu: MenuItem
    private lateinit var allListGoals: MutableList<Goal>
    private var beforeChangedListNameInET = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_items_list)

        textIETNameListGoals = findViewById(R.id.textIETNameListGoals)
        textIETNameNewGoal = findViewById(R.id.textIETNameNewGoal)
        ivCurrentIconList = findViewById(R.id.ivEditParentIcon)
        addNewItemToListImgBtn = findViewById(R.id.fabAddGoalToParentList)
        recyclerViewEditGoals = findViewById(R.id.recyclerViewEditGoals)
        goalDBHelper = GoalDBOpenHelper(this)
        initData()
        createRecycler()
        addListNameETListener()
    }

    private fun initData() {
        val intent = intent.getSerializableExtra("currentObjListName")
        if (intent != null) {
            intentCurrentParentListName = intent as ParentListGoals
            textIETNameListGoals.text = intentCurrentParentListName.nameList.toEditable()
            ivCurrentIconList.setImageResource(intentCurrentParentListName.listIcon)
            currentParentListName = intentCurrentParentListName
            beforeChangedListNameInET = currentParentListName!!.nameList
            initAllGoalsByName()
        } else {
            println("NullIntent")
            allListGoals = mutableListOf()
        }
    }

    private fun initAllGoalsByName() {
        if (this::allListGoals.isInitialized && allListGoals.isNotEmpty())
            allListGoals.toMutableList().clear()
        allListGoals = goalDBHelper.queryGoals(currentParentListName?.id.toString()).toMutableList()
    }

    private fun createRecycler() {
//        itemGoalsAdapter =
//            ItemsGoalAdapters(
//                this,
//                allListGoals.toTypedArray(),
//                this
//            )
        recyclerViewEditGoals.apply {
            layoutManager = LinearLayoutManager(context)
            hasFixedSize()
            itemAnimator = DefaultItemAnimator()
            adapter = itemsGoalsAdapterRecycler
        }
    }

    private fun addListNameETListener() =
        textIETNameListGoals.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                changedListNameInET = s.toString()
                myItemSaveMenu.isVisible = changedListNameInET != beforeChangedListNameInET
            }
        })


    private fun updateCurrentListName(name: String) {
        beforeChangedListNameInET = name
        currentParentListName = goalDBHelper.queryLists(null, name).first()
    }

    fun addNewItemGoalToDB(view: View) {
        val nameGoalList = textIETNameListGoals.text.toString().trim()
        val nameGoal = textIETNameNewGoal.text.toString().trim()
        val rootView: View = window.decorView.findViewById(android.R.id.content)
        when {
            nameGoalList.isEmpty() ->
                Snackbar.make(rootView, "Input list name", Snackbar.LENGTH_SHORT).show()
            nameGoal.isEmpty() ->
                Snackbar.make(rootView, "Input goal name", Snackbar.LENGTH_SHORT).show()
            else -> {
                saveDataToDB(nameGoal, nameGoalList)
                initAllGoalsByName()
//                itemGoalsAdapter.updateData(allListGoals.toTypedArray())
            }
        }
    }

    private fun saveDataToDB(nameGoal: String?, nameGoalList: String?) {
        if (currentParentListName == null && nameGoalList != null) {
            ContentValues().apply {
                put(COLUMN_NAME_LIST, nameGoalList)
                put(COLUMN_ICON_GOAL, changedIcon ?: R.drawable.ic_purpose)
                val uri = contentResolver.insert(CONTENT_URI_LIST, this)
                if (uri == null) Log.d(MY_TAG, "ListName insertion into table failed")
                else Log.d(MY_TAG, "ListName insertion is successful")
            }
            updateCurrentListName(nameGoalList)
            println("Update List")
        }
        if (nameGoal != null) {
            ContentValues().apply {
                put(COLUMN_NAME_GOAL, nameGoal)
                put(COLUMN_ID_LIST, currentParentListName?.id)
                val uri = contentResolver.insert(CONTENT_URI_GOAL, this)
                if (uri == null) Log.d(MY_TAG, "Insertion goal in the table failed")
                else Log.d(MY_TAG, "Insertion goal is successful")
            }
            textIETNameNewGoal.text?.clear()
        }
    }

    private fun updateListName() {
        ContentValues().apply {
            put(COLUMN_NAME_LIST, changedListNameInET)
            val uri = Uri.withAppendedPath(CONTENT_URI_LIST, currentParentListName?.id.toString())
            val up = contentResolver.update(uri, this, null, null)
            if (up == 0) Log.d(MY_TAG, "Updated listName in table failed")
            else Log.d(MY_TAG, "Updating listName is successfully")
        }
    }

    private fun updateListIcon() {
        if (currentParentListName != null && changedIcon != null) {
            ContentValues().apply {
                put(COLUMN_ICON_GOAL, changedIcon)
                val uri = Uri.withAppendedPath(CONTENT_URI_LIST, currentParentListName?.id.toString())
                val up = contentResolver.update(uri, this, null, null)
                if (up == 0) Log.d(MY_TAG, "Updated iconList in table failed")
                else Log.d(MY_TAG, "Updating iconList is successfully")
            }
            currentParentListName?.listIcon = changedIcon!!
        }
        changedIcon?.let { ivCurrentIconList.setImageResource(it) }
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
                if (currentParentListName != null) updateListName()
                else saveDataToDB(null, changedListNameInET)
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

    override fun onPause() {
        super.onPause()
        if (currentParentListName != null && allListGoals.isEmpty()) {
            val uri = Uri.withAppendedPath(CONTENT_URI_LIST, currentParentListName!!.id.toString())
            val del = goalDBHelper.delListGoalsByListName(uri, null, null)
            println("Delete Goal: $del")
        }
    }

    fun ivChooseIconForListGoals(view: View) {
        val intent = Intent(this, ChoosingNewIconForListName::class.java)
        println("EditAct IconId: ${currentParentListName?.listIcon}")
        intent.putExtra("currentIconName", currentParentListName?.listIcon)
        startActivityForResult(intent, REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && data != null)
            changedIcon = data.getIntExtra("selectedIconName", R.drawable.ic_purpose)
        updateListIcon()
    }

    override fun onDeleteListener(position: Int) {
        val goal = allListGoals[position]
        allListGoals.remove(goal)
    }

    companion object {
        const val REQUEST_CODE = 111
        const val MY_TAG = "MyTag"
    }
}


/*
* for dialog

    fun ivChooseIconForListGoals(view: View) {
        val myDialogIcons = ChooseIconDialogFragment()
        val args = Bundle()
        args.putInt("currentIdList", idListNames)
        val manager = supportFragmentManager
        myDialogIcons.arguments = args
        myDialogIcons.show(manager, "DialogIcons")
    }*/