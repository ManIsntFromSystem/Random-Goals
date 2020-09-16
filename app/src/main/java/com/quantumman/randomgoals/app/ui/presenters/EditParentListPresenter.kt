package com.quantumman.randomgoals.app.ui.presenters

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.quantumman.randomgoals.R
import com.quantumman.randomgoals.app.interactors.EditParentListWithGoalsInteractor
import com.quantumman.randomgoals.app.model.GoalItem
import com.quantumman.randomgoals.app.model.ParentWithListGoals
import com.quantumman.randomgoals.app.ui.views.EditParentListsView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import moxy.InjectViewState
import moxy.MvpPresenter
import java.util.*

@InjectViewState
class EditParentListPresenter(
    private val parentWithGoalsInteractor: EditParentListWithGoalsInteractor
) : MvpPresenter<EditParentListsView>() {

    private val _data = MutableLiveData<ParentWithListGoals>()
    val data: LiveData<ParentWithListGoals> = _data

    init {
        _data.value = ParentWithListGoals(1, 1111111L, "Parks", "ic_draw", emptyList())
    }

    fun showData() {
        viewState.startListeningData()
        viewState.initComponents()
    }

    fun statusData(existence: Boolean) {
        viewState.changeStateRecycler(existence = existence)
        Handler(Looper.getMainLooper()).postDelayed({
            _data.value = ParentWithListGoals(1, 1111111L, "Parks", "ic_draw",
                listOf(GoalItem(2, 111111L, "Zari",1 )))
        }, 2000L)
    }

    fun saveWholeParentList(parentWithListGoals: ParentWithListGoals) {
        TODO("Not yet implemented")
    }

    fun editParentName(newName: String) {
        parentWithGoalsInteractor.getAllParentNames()
            .map { it.contains(newName).not() }
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { name ->
                if (name) {
                    println("Parent name: $newName")
                } else {
                    viewState.showError(R.string.error_name_exist)
                }
            }
    }

    fun saveNewGoalItem(name: String) {
        val date = Calendar.getInstance().timeInMillis
        val parentId = data.value?.parentId
        val goalItem = GoalItem(0, goalDate = date, goalName = name, parentList = 0)
    }

    fun deleteParentList() {

    }

    fun deleteGoalItem(goal: GoalItem) {
        TODO("Not yet implemented")
    }

    fun deleteAllGoals() {
        TODO("Not yet implemented")
    }
}