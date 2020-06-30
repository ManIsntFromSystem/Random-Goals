package com.quantumman.randomgoals.app.views

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
import com.quantumman.randomgoals.app.model.Goal
import com.quantumman.randomgoals.app.model.ListNames
import com.quantumman.randomgoals.data.ItemsGoalAdapters
import com.quantumman.randomgoals.data.helpers.GoalDBOpenHelper
import com.quantumman.randomgoals.data.helpers.GoalsContract.GoalsListEntry.COLUMN_ICON_GOAL
import com.quantumman.randomgoals.data.helpers.GoalsContract.GoalsListEntry.COLUMN_NAME_LIST
import com.quantumman.randomgoals.data.helpers.GoalsContract.GoalsListEntry.CONTENT_URI_LIST
import com.quantumman.randomgoals.data.helpers.GoalsContract.ItemGoalEntry.COLUMN_ID_LIST
import com.quantumman.randomgoals.data.helpers.GoalsContract.ItemGoalEntry.COLUMN_NAME_GOAL
import com.quantumman.randomgoals.data.helpers.GoalsContract.ItemGoalEntry.CONTENT_URI_GOAL
import com.quantumman.randomgoals.util.toEditable

class EditItemsListActivity : AppCompatActivity() {
    private lateinit var addNewItemToListImgBtn: FloatingActionButton
    private lateinit var recyclerViewEditGoals: RecyclerView
    private lateinit var ivCurrentIconList: ImageView
    private lateinit var etNameNewGoal: EditText
    private lateinit var etNameListGoals: EditText
    private lateinit var itemGoalsAdapter: ItemsGoalAdapters
    private lateinit var goalDBHelper: GoalDBOpenHelper
    private lateinit var intentCurrentListName: ListNames
    private lateinit var currentListName: ListNames
    private lateinit var changedListNameInET: String
    private lateinit var myItemSaveMenu: MenuItem
    private lateinit var allListGoals: List<Goal>
    private var beforeChangedListNameInET = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_items_list)

        etNameListGoals = findViewById(R.id.etNameListGoals)
        etNameNewGoal = findViewById(R.id.etNameNewGoal)
        ivCurrentIconList = findViewById(R.id.ivCurrentIconList)
        addNewItemToListImgBtn = findViewById(R.id.addNewItemToListImgBtn)
        recyclerViewEditGoals = findViewById(R.id.recyclerViewEditGoals)
        goalDBHelper = GoalDBOpenHelper(this)
        initData()
        createRecycler()
        addListNameETListener()
    }

    private fun initData() {
        if (intent != null) {
            intentCurrentListName = intent.getSerializableExtra("currentObjListName") as ListNames
            etNameListGoals.text = intentCurrentListName.nameList.toEditable()
            ivCurrentIconList.setImageResource(intentCurrentListName.listIcon)
            updateCurrentListName(intentCurrentListName.nameList)
            initAllGoalsByName()
        } else println("NullIntent")
    }

    private fun updateCurrentListName(name: String) {
        beforeChangedListNameInET = name
        currentListName = goalDBHelper.queryLists(null, name).first()
    }

    private fun initAllGoalsByName() {
        if (this::allListGoals.isInitialized && allListGoals.isNotEmpty())
            allListGoals.toMutableList().clear()
        allListGoals = goalDBHelper.queryGoals(intentCurrentListName.id.toString())
    }

    private fun createRecycler() {
        itemGoalsAdapter = ItemsGoalAdapters(this, allListGoals.toTypedArray())
        recyclerViewEditGoals.apply {
            layoutManager = LinearLayoutManager(context)
            hasFixedSize()
            itemAnimator = DefaultItemAnimator()
            adapter = itemGoalsAdapter
        }
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

    fun addNewItemGoalToDB(view: View) {
        val nameGoalList = etNameListGoals.text.toString().trim()
        val nameGoal = etNameNewGoal.text.toString().trim()
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
        if (!this::intentCurrentListName.isInitialized && nameGoalList != null) {
            ContentValues().apply {
                put(COLUMN_NAME_LIST, nameGoalList)
                put(COLUMN_ICON_GOAL, ivCurrentIconList.tag.toString())
                val uri = contentResolver.insert(CONTENT_URI_LIST, this)
                if (uri == null) Log.d(MY_TAG, "ListName insertion into table failed")
                else Log.d(MY_TAG, "ListName insertion is successful")
            }
            updateCurrentListName(nameGoalList)
        }
        if (nameGoal != null) {
            ContentValues().apply {
                put(COLUMN_NAME_GOAL, nameGoal)
                put(COLUMN_ID_LIST, intentCurrentListName.id)
                val uri = contentResolver.insert(CONTENT_URI_GOAL, this)
                if (uri == null) Log.d(MY_TAG, "Insertion goal in the table failed")
                else Log.d(MY_TAG, "Insertion goal is successful")
            }
            etNameNewGoal.text.clear()
        }
    }

    private fun updateListName() {
        ContentValues().apply {
            put(COLUMN_NAME_LIST, changedListNameInET)
            val uri = Uri.withAppendedPath(CONTENT_URI_LIST, currentListName.id.toString())
            val up = contentResolver.update(uri, this, null, null)
            if (up == 0) Log.d(MY_TAG, "Updated listName in table failed")
            else Log.d(MY_TAG, "Updating listName is successfully")
        }
    }

    private fun updateListIcon(iconName: Int) {
        ContentValues().apply {
            put(COLUMN_ICON_GOAL, iconName)
            val uri = Uri.withAppendedPath(CONTENT_URI_LIST, currentListName.id.toString())
            val up = contentResolver.update(uri, this, null, null)
            if (up == 0) Log.d(MY_TAG, "Updated iconList in table failed")
            else Log.d(MY_TAG, "Updating iconList is successfully")
        }
        currentListName.listIcon = iconName
        ivCurrentIconList.setImageResource(iconName)
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
                if (this::currentListName.isInitialized) updateListName()
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
        if (this::currentListName.isInitialized && allListGoals.isEmpty()) {
            val uri = Uri.withAppendedPath(CONTENT_URI_LIST, currentListName.id.toString())
            goalDBHelper.delListGoalsByListName(uri, null, null)
        }
    }

    fun ivChooseIconForListGoals(view: View) {
        val intent = Intent(this, ChoosingNewIconForListName::class.java)
        println("EditAct IconId: ${currentListName.listIcon}")
        intent.putExtra("currentIconName", currentListName.listIcon)
        startActivityForResult(intent, REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && data != null)
            updateListIcon(data.getIntExtra("selectedIconName", R.drawable.ic_purpose))
    }

    companion object {
        const val REQUEST_CODE = 111
        const val MY_TAG = "TagEditActivity: "
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