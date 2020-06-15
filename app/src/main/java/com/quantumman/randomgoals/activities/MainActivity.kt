package com.quantumman.randomgoals.activities

import android.content.Intent
import android.database.Cursor
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.quantumman.randomgoals.R
import com.quantumman.randomgoals.data.GoalDBOpenHelper
import com.quantumman.randomgoals.data.GoalsContentProvider
import com.quantumman.randomgoals.data.GoalsContract.GoalsListEntry.COLUMN_ICON_GOAL
import com.quantumman.randomgoals.data.GoalsContract.GoalsListEntry.CONTENT_URI
import com.quantumman.randomgoals.data.GoalsContract.GoalsListEntry.CONTENT_URI_LIST
import com.quantumman.randomgoals.data.GoalsContract.GoalsListEntry.ID_LIST
import com.quantumman.randomgoals.data.GoalsContract.GoalsListEntry.TABLE_NAME_LIST
import com.quantumman.randomgoals.model.Goal

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var fabEditGoalBtnMain: ImageView
    private lateinit var playRandomImgBtn: ImageView
    private lateinit var iconChosenGoalImg: ImageView
    private lateinit var startedRandomGoalImg: ImageView
    private lateinit var nameChosenGoalTextView: TextView
    private lateinit var relativeChosenGoal: RelativeLayout
    private lateinit var spinnerListsGoals: Spinner
    private lateinit var initialGoalsLists: List<String>
    private lateinit var selectedGoalsList: List<Goal>
    private lateinit var content: GoalsContentProvider
    private lateinit var goalsDBHelper: GoalDBOpenHelper
    private lateinit var mapGoalsForRandom: MutableMap<String, String>
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
        goalsDBHelper = GoalDBOpenHelper(this)
        mapGoalsForRandom = mutableMapOf()
        initLists()
        createSpinner()
    }

    private fun createSpinner() =
        ArrayAdapter(this, android.R.layout.simple_spinner_item, initialGoalsLists)
            .also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinnerListsGoals.adapter = adapter
            }

    private fun initLists() {
        if (this::initialGoalsLists.isInitialized && initialGoalsLists.isNotEmpty()){
            initialGoalsLists.toMutableList().clear()
        }
        contentResolver.query(CONTENT_URI_LIST, arrayOf(ID_LIST, TABLE_NAME_LIST, COLUMN_ICON_GOAL), null, null, null, null, null)
        initialGoalsLists
    }

    override fun onResume() {
        super.onResume()
        println("OnResume Init")
        if (point > 0) {
            initLists()
            createSpinner()
            println("OnResume Init")
        }
        point++
    }

    fun editGoalsFab(view: View) {
        val intent = Intent(this, ListGoalsActivity::class.java)
        startActivity(intent)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        println("OnNothingSelected")
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        relativeChosenGoal.visibility = View.INVISIBLE
        startedRandomGoalImg.visibility = View.VISIBLE
        if (mapGoalsForRandom.isNotEmpty()) mapGoalsForRandom.clear()
        selectedGoalsList = GoalDBOpenHelper(this).queryGoals(initialGoalsLists[position])
        for (i in selectedGoalsList.indices)
            mapGoalsForRandom[selectedGoalsList[i].nameGoal] = selectedGoalsList[i].iconGoal
    }

    fun getRandomGoal(view: View) {
        val randomGoal = mapGoalsForRandom.keys.shuffled().first()
        val imageResource = this.resources
            .getIdentifier(mapGoalsForRandom[randomGoal], "drawable", this.packageName)
        iconChosenGoalImg.setImageResource(imageResource)
        nameChosenGoalTextView.text = randomGoal
        startedRandomGoalImg.visibility = View.INVISIBLE
        relativeChosenGoal.visibility = View.VISIBLE
    }

    companion object{
        private const val MY_TAG = "MyLog"
    }
}
