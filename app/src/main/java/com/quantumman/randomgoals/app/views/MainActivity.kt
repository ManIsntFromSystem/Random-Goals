package com.quantumman.randomgoals.app.views

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.quantumman.randomgoals.R
import com.quantumman.randomgoals.data.GoalsContentProvider
import com.quantumman.randomgoals.data.helpers.GoalsContract.GoalsListEntry.COLUMN_ICON_GOAL
import com.quantumman.randomgoals.data.helpers.GoalsContract.GoalsListEntry.COLUMN_NAME_LIST
import com.quantumman.randomgoals.data.helpers.GoalsContract.GoalsListEntry.CONTENT_URI_LIST
import com.quantumman.randomgoals.data.helpers.GoalsContract.GoalsListEntry.ID_LIST
import com.quantumman.randomgoals.data.helpers.GoalsContract.ItemGoalEntry.COLUMN_ID_LIST
import com.quantumman.randomgoals.data.helpers.GoalsContract.ItemGoalEntry.COLUMN_NAME_GOAL
import com.quantumman.randomgoals.data.helpers.GoalsContract.ItemGoalEntry.CONTENT_URI_GOAL
import com.quantumman.randomgoals.data.helpers.GoalsContract.ItemGoalEntry.ID_GOAL
import com.quantumman.randomgoals.app.model.Goal
import com.quantumman.randomgoals.app.model.ListNames
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var fabEditGoalBtnMain: ImageView
    private lateinit var playRandomImgBtn: ImageView
    private lateinit var iconChosenGoalImg: ImageView
    private lateinit var startedRandomGoalImg: ImageView
    private lateinit var nameChosenGoalTextView: TextView
    private lateinit var relativeChosenGoal: RelativeLayout
    private lateinit var spinnerListsGoals: Spinner
    private lateinit var initialListOfLists: MutableList<ListNames>
    private lateinit var initialListOfGoals: MutableList<Goal>
    private lateinit var goalListNames: List<String>
    private lateinit var selectedGoalsList: List<Goal>
    private lateinit var content: GoalsContentProvider
    private lateinit var mapGoalsForRandom: MutableMap<String, Int>
    private var point: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fabEditGoalBtnMain = findViewById(R.id.fabEditGoalBtnMain)
        playRandomImgBtn = findViewById(R.id.playRandomImgBtn)
        startedRandomGoalImg = findViewById(R.id.startedRandomGoalImg)
        iconChosenGoalImg = findViewById(R.id.iconChosenGoalImg)
        nameChosenGoalTextView = findViewById(R.id.nameChosenGoalTextView)
        relativeChosenGoal = findViewById(R.id.relativeChosenGoal)
        spinnerListsGoals = findViewById(R.id.spinnerListsGoals)
        spinnerListsGoals.onItemSelectedListener = this
        content = GoalsContentProvider()
        initialListOfLists = mutableListOf()
        initialListOfGoals = mutableListOf()
        mapGoalsForRandom = mutableMapOf()
        updateAllData()
    }

    private fun updateAllData() {
        initLists()
        getAllGoals()

        if (this::goalListNames.isInitialized && goalListNames.isNotEmpty())
            goalListNames.toMutableList().clear()
        goalListNames = initialListOfLists.map { it.nameList }
        ArrayAdapter(this, android.R.layout.simple_spinner_item, goalListNames)
            .also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinnerListsGoals.adapter = adapter
            }
    }

    private fun initLists() {
        if (initialListOfLists.isNotEmpty()) initialListOfLists.clear()
        val cursor =  contentResolver.query(CONTENT_URI_LIST,null,
            null, null, null)
        if (cursor != null) {
            while (cursor.moveToNext()) {
                val itemListId = cursor.getInt(cursor.getColumnIndex(ID_LIST))
                val itemListName = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_LIST))
                val itemListIcon = cursor.getInt(cursor.getColumnIndex(COLUMN_ICON_GOAL))
                initialListOfLists.plusAssign(
                    ListNames(
                        itemListId,
                        itemListName,
                        itemListIcon
                    )
                )
            }
            cursor.close()
        }
    }

    override fun onResume() {
        super.onResume()
        when {
            point > 0 -> updateAllData()
            point in 0..1 -> point++
        }
    }

    fun editGoalsFab(view: View) {
        val intent = Intent(this, ListGoalsActivity::class.java)
        startActivity(intent)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        println("OnNothingSelected")
    }

    private fun getAllGoals() {
        if (initialListOfGoals.isNotEmpty()) initialListOfGoals.clear()
        val cursor = contentResolver.query(CONTENT_URI_GOAL,null,null,
            null,null
        )
        if (cursor != null) {
            while (cursor.moveToNext()) {
                val itemGoalId = cursor.getInt(cursor.getColumnIndex(ID_GOAL))
                val itemGoalName = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_GOAL))
                val itemIdList = cursor.getInt(cursor.getColumnIndex(COLUMN_ID_LIST))
                initialListOfGoals.plusAssign(
                    Goal(
                        itemGoalId,
                        itemGoalName,
                        itemIdList
                    )
                )
            }
            cursor.close()
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        relativeChosenGoal.visibility = View.INVISIBLE
        startedRandomGoalImg.visibility = View.VISIBLE
        if (mapGoalsForRandom.isNotEmpty())
            mapGoalsForRandom.clear()
        selectedGoalsList = initialListOfGoals.filter { it.goalListNames == position + 1}
        selectedGoalsList.forEach {
            mapGoalsForRandom[it.nameGoal] = initialListOfLists[position].listIcon
        }
    }

    fun getRandomGoal(view: View) = if (mapGoalsForRandom.isNotEmpty()){
        val randomGoal = mapGoalsForRandom.keys.shuffled().first()
//        val imageResource = this.resources
//            .getIdentifier(mapGoalsForRandom[randomGoal], "drawable", this.packageName)
        iconChosenGoalImg.setImageResource(mapGoalsForRandom[randomGoal]!!)
        nameChosenGoalTextView.text = randomGoal
        startedRandomGoalImg.visibility = View.INVISIBLE
        relativeChosenGoal.visibility = View.VISIBLE
    } else Snackbar.make(snack_activity_main, "Add several goals", Snackbar.LENGTH_LONG).show()

    companion object{
        private const val MY_TAG = "MyLog"
    }
}
