package com.quantumman.randomgoals.app.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.quantumman.randomgoals.R
import com.quantumman.randomgoals.app.ui.fragments.EditParentListFragment
import com.quantumman.randomgoals.app.ui.fragments.RandomScreenFragment
import com.quantumman.randomgoals.helpers.HandleSnackMessage
import com.quantumman.randomgoals.helpers.extensions.showSnack

class MainActivity : AppCompatActivity(), HandleSnackMessage/*AdapterView.OnItemSelectedListener*/ {
//    private lateinit var fabEditGoalBtnMain: ImageView
//    private lateinit var playRandomImgBtn: ExtendedFloatingActionButton
//    private lateinit var iconChosenGoalImg: ImageView
//    private lateinit var startedRandomGoalImg: ImageView
//    private lateinit var nameChosenGoalTextView: TextView
//    private lateinit var relativeChosenGoal: RelativeLayout
//    private lateinit var spinnerListsGoals: Spinner
//    private lateinit var initialListOfParentLists: MutableList<ParentListGoals>
//    private lateinit var initialListOfGoals: MutableList<Goal>
//    private lateinit var goalListNames: List<String>
//    private lateinit var selectedGoalsList: List<Goal>
//    private lateinit var mapGoalsForRandom: MutableMap<String, Int>
//    private var point: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        window.requestFeature(Window.FEATURE_CONTENT_TRANSITIONS)
        setContentView(R.layout.activity_main)

        val fragment = EditParentListFragment.getCreateParentInstance()
        supportFragmentManager.beginTransaction()
            .disallowAddToBackStack()
            .replace(R.id.container, fragment, TAG)
            .commitAllowingStateLoss()
    }

    override fun showMessage(message: String) {
        showSnack(text = message)
    }

    companion object {
        private val TAG = MainActivity::class.simpleName
    }
}


//        fabEditGoalBtnMain = findViewById(R.id.fabEditGoalBtnMain)
//        playRandomImgBtn = findViewById(R.id.playRandomImgBtn)
//        startedRandomGoalImg = findViewById(R.id.startedRandomGoalImg)
//        iconChosenGoalImg = findViewById(R.id.iconChosenGoalImg)
//        nameChosenGoalTextView = findViewById(R.id.nameChosenGoalTextView)
//        relativeChosenGoal = findViewById(R.id.relativeChosenGoal)
//        spinnerListsGoals = findViewById(R.id.spinnerListsGoals)
//        spinnerListsGoals.onItemSelectedListener = this
//        initialListOfParentLists = mutableListOf()
//        initialListOfGoals = mutableListOf()
//        mapGoalsForRandom = mutableMapOf()
//        updateAllData()

//
//    private fun updateAllData() {
//        initLists()
//        getAllGoals()
//
//        if (this::goalListNames.isInitialized && goalListNames.isNotEmpty())
//            goalListNames.toMutableList().clear()
//        goalListNames = initialListOfParentLists.map { it.nameList }
//        ArrayAdapter(this, android.R.layout.simple_spinner_item, goalListNames)
//            .also { adapter ->
//                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//                spinnerListsGoals.adapter = adapter
//            }
//    }
//
//    private fun initLists() {
//        if (initialListOfParentLists.isNotEmpty()) initialListOfParentLists.clear()
//        val cursor =  contentResolver.query(CONTENT_URI_LIST,null,
//            null, null, null)
//        if (cursor != null) {
//            while (cursor.moveToNext()) {
//                val itemListId = cursor.getInt(cursor.getColumnIndex(ID_LIST))
//                val itemListName = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_LIST))
//                val itemListIcon = cursor.getInt(cursor.getColumnIndex(COLUMN_ICON_GOAL))
//                initialListOfParentLists.plusAssign(
//                    ParentListGoals(
//                        itemListId,
//                        itemListName,
//                        itemListIcon
//                    )
//                )
//            }
//            cursor.close()
//        }
//    }
//
//    override fun onResume() {
//        super.onResume()
//        when {
//            point > 0 -> updateAllData()
//            point in 0..1 -> point++
//        }
//    }
//
//    fun editGoalsFab(view: View) {
//        window.exitTransition = Explode()
//        val options = ActivityOptions.makeSceneTransitionAnimation(this).toBundle()
//        val intent = Intent(this, ListGoalsActivity::class.java)
//        startActivity(intent, options)
//    }
//
//    override fun onNothingSelected(parent: AdapterView<*>?) {
//        println("OnNothingSelected")
//    }
//
//    private fun getAllGoals() {
//        if (initialListOfGoals.isNotEmpty()) initialListOfGoals.clear()
//        val cursor = contentResolver.query(CONTENT_URI_GOAL,null,null,
//            null,null
//        )
//        if (cursor != null) {
//            while (cursor.moveToNext()) {
//                val itemGoalId = cursor.getInt(cursor.getColumnIndex(ID_GOAL))
//                val itemGoalName = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_GOAL))
//                val itemIdList = cursor.getInt(cursor.getColumnIndex(COLUMN_ID_LIST))
//                initialListOfGoals.plusAssign(
//                    Goal(
//                        itemGoalId,
//                        itemGoalName,
//                        itemIdList
//                    )
//                )
//            }
//            cursor.close()
//        }
//    }
//
//    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
//        relativeChosenGoal.visibility = View.INVISIBLE
//        startedRandomGoalImg.visibility = View.VISIBLE
//        if (mapGoalsForRandom.isNotEmpty())
//            mapGoalsForRandom.clear()
//        selectedGoalsList = initialListOfGoals.filter { it.parentList == position + 1}
//        selectedGoalsList.forEach {
//            mapGoalsForRandom[it.nameGoal] = initialListOfParentLists[position].listIcon
//        }
//    }
//
//    fun getRandomGoal(view: View) = if (mapGoalsForRandom.isNotEmpty()){
//        val randomGoal = mapGoalsForRandom.keys.shuffled().first()
//        iconChosenGoalImg.setImageResource(mapGoalsForRandom[randomGoal]!!)
//        nameChosenGoalTextView.text = randomGoal
//        startedRandomGoalImg.visibility = View.GONE
//        relativeChosenGoal.visibility = View.VISIBLE
//    } else Snackbar.make(snack_activity_main, "Add several goals", Snackbar.LENGTH_LONG).show()
