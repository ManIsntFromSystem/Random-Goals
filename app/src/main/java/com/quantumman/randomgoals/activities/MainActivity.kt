package com.quantumman.randomgoals.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.quantumman.randomgoals.R
import com.quantumman.randomgoals.data.GoalDBOpenHelper
import com.quantumman.randomgoals.data.GoalsContentProvider
import com.quantumman.randomgoals.model.Goal

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var fabEditGoalBtnMain: ImageView
    private lateinit var playRandomImgBtn: ImageView
    private lateinit var iconChosenGoalImg: ImageView
    private lateinit var startedRandomGoalImg: ImageView
    private lateinit var nameChosenGoalTextView: TextView
    private lateinit var relativeChosenGoal: RelativeLayout
    private lateinit var spinnerListsGoals: Spinner
    private lateinit var initialGoalsLists: MutableList<String>
    private lateinit var selectedGoalsList: List<Goal>
    private lateinit var content: GoalsContentProvider
    private lateinit var mapGoalsForRandom: MutableMap<String, String>

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
        mapGoalsForRandom = mutableMapOf()
        initialGoalsLists = mutableListOf()
        updateListInitialListsForSpinner()
        createSpinner()
    }

    private fun createSpinner() =
        ArrayAdapter(this, android.R.layout.simple_spinner_item, initialGoalsLists)
            .also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinnerListsGoals.adapter = adapter
            }

    private fun updateListInitialListsForSpinner() {
        if (initialGoalsLists.isNotEmpty()) initialGoalsLists.clear()
        initialGoalsLists.addAll(GoalDBOpenHelper(this).queryGoals(null)
            .map { it.nameListGoals }
            .distinct()
            .toList())
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
