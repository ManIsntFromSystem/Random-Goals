package com.quantumman.randomgoals.activities

import android.content.ContentValues
import android.os.Bundle
import android.text.Editable
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
import com.quantumman.randomgoals.data.GoalDBOpenHelper
import com.quantumman.randomgoals.data.GoalsContentProvider
import com.quantumman.randomgoals.data.GoalsContract.MemberEntry.COLUMN_NAME_ICON_GOAL
import com.quantumman.randomgoals.data.GoalsContract.MemberEntry.COLUMN_NAME_ITEM_GOAL
import com.quantumman.randomgoals.data.GoalsContract.MemberEntry.COLUMN_NAME_LIST
import com.quantumman.randomgoals.data.GoalsContract.MemberEntry.CONTENT_URI
import com.quantumman.randomgoals.data.ItemsGoalAdapters
import com.quantumman.randomgoals.data.RecyclerItemClickListener
import com.quantumman.randomgoals.model.Goal
import kotlinx.android.synthetic.main.activity_edit_items_list.*


class EditItemsListActivity : AppCompatActivity() {
    private lateinit var nameListGoals: EditText
    private lateinit var nameNewItemEditText: EditText
    private lateinit var iconNewItemImageView: ImageView
    private lateinit var addNewItemToListImgBtn: FloatingActionButton
    private lateinit var recyclerViewEditGoals: RecyclerView
    private lateinit var goalContentProvider: GoalsContentProvider
    private lateinit var goalsDBHelper: GoalDBOpenHelper
    private lateinit var allListGoals: List<Goal>
    private var currentListName: String? = null
    private val itemsGoalMap = mutableMapOf<String, String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_items_list)

        nameListGoals = findViewById(R.id.nameListGoals)
        nameNewItemEditText = findViewById(R.id.nameNewItemEditText)
        iconNewItemImageView = findViewById(R.id.iconNewItemImageView)
        addNewItemToListImgBtn = findViewById(R.id.addNewItemToListImgBtn)
        recyclerViewEditGoals = findViewById(R.id.recyclerViewEditGoals)
        goalContentProvider = GoalsContentProvider()
        goalsDBHelper = GoalDBOpenHelper(this)
        currentListName = intent.getStringExtra("list_name")
        when (currentListName) {
            null -> allListGoals = listOf()
            else -> {
                nameListGoals.text = currentListName!!.toEditable()
                initAllGoalsByName()
            }
        }
        getGoals()
    }

    private fun initAllGoalsByName() {
        val name = nameListGoals.text.toString().trim()
        if (this::allListGoals.isInitialized && allListGoals.isNotEmpty())
            allListGoals.toMutableList().clear()
        allListGoals = goalsDBHelper.queryGoals(name)
    }

    private fun getGoals() = recyclerViewEditGoals.apply {
        layoutManager = LinearLayoutManager(context)
        hasFixedSize()
        itemAnimator = DefaultItemAnimator()
        adapter = ItemsGoalAdapters(context, allListGoals)
        /*addOnItemTouchListener(RecyclerItemClickListener(context, this,
                object : RecyclerItemClickListener.OnItemClickListener {
                    override fun onItemClick(view: View?, position: Int) {
                        Log.d("MyTag", "This is onItemClick---${view?.let {
                            this@apply.getChildAdapterPosition(it)}} ------ $position")
                    }

                    override fun onLongItemClick(view: View?, position: Int) {
                        Log.d("MyTag", "This is onLongItemClick")
                    }
                })
        )*/
    }


    fun addNewItemToMap(view: View) {
        val nameGoal = nameNewItemEditText.text.toString().trim()
        val nameList = nameNewItemEditText.text.toString().trim()
        if (nameGoal.isNotEmpty()) {
            itemsGoalMap.plusAssign(nameGoal to iconNewItemImageView.tag.toString())
            nameNewItemEditText.text.clear()
        } else Snackbar.make(rootLayout, "Input your goal.", Snackbar.LENGTH_SHORT)
        saveListGoalToDB()
        itemsGoalMap.clear()
        initAllGoalsByName()
        recyclerViewEditGoals.adapter = ItemsGoalAdapters(this, allListGoals)
    }

    /*tailrec fun checkTheSameNameList() {
        val listAllNamesLists = goalsDBHelper.queryGoals(null).toSet().map { it.nameListGoals }
        if (listAllNamesLists.contains(inputListName)) inputListName // add tailrec fun for check
        else inputListName + "1"
    }*/

    private fun saveListGoalToDB() {
        val inputListName = if (currentListName != null) currentListName
        else nameListGoals.text.toString()
        when {
            inputListName!!.isEmpty() ->
                Snackbar.make(rootLayout, "Input the name", Snackbar.LENGTH_SHORT).show()
            itemsGoalMap.isEmpty() ->
                Snackbar.make(rootLayout, "Input several goals", Snackbar.LENGTH_SHORT).show()
        }
        val contentValues = ContentValues()
        val contentResolver = contentResolver
        for (i in itemsGoalMap.keys.indices) {
            contentValues.apply {
                put(COLUMN_NAME_LIST, inputListName)
                put(COLUMN_NAME_ITEM_GOAL, itemsGoalMap.keys.toList()[i])
                put(COLUMN_NAME_ICON_GOAL, itemsGoalMap.values.toList()[i])
                val uri = contentResolver.insert(CONTENT_URI, contentValues)
                if (uri == null)
                    Log.d("MyLog", "Insertion of data in the table failed")
                else Log.d("MyLog", "Insertion of data is successful")
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_edit_goal_items, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.save_list_goals -> {
                saveListGoalToDB() // add check update or add
                return true
            }
            android.R.id.home -> {
                NavUtils.navigateUpFromSameTask(this)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}

private fun String.toEditable() = Editable.Factory.getInstance().newEditable(this)
