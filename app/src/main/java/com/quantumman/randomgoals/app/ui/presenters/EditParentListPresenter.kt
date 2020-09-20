package com.quantumman.randomgoals.app.ui.presenters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.quantumman.randomgoals.R
import com.quantumman.randomgoals.app.interactors.EditParentListWithGoalsInteractor
import com.quantumman.randomgoals.app.model.GoalItem
import com.quantumman.randomgoals.app.model.ParentWithListGoals
import com.quantumman.randomgoals.app.ui.views.EditParentListsView
import com.quantumman.randomgoals.helpers.enums.ParentListMode
import com.quantumman.randomgoals.helpers.enums.RecyclerClickType
import com.quantumman.randomgoals.helpers.extensions.incrementDefaultName
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import moxy.InjectViewState
import moxy.MvpPresenter
import org.joda.time.DateTime
import timber.log.Timber

@InjectViewState
class EditParentListPresenter(
    private val parentEditInteractor: EditParentListWithGoalsInteractor
) : MvpPresenter<EditParentListsView>() {

    // For clear disposables
    private val disposableBag = CompositeDisposable()

    //for update UI
    private val _data = MutableLiveData<ParentWithListGoals>()
    val data: LiveData<ParentWithListGoals> = _data

    //set default Parent list when - Edit mode
    fun startCreationFragment(mode: ParentListMode, name: String) {
        when (mode) {
            ParentListMode.CREATION -> {
                val defDate = DateTime.now().millis
                val defaultName: String = generateUniqueDefaultName(name)
                val parent = ParentWithListGoals(
                    parentName = defaultName,
                    parentIcon = 1,
                    parentDate = defDate,
                    listGoals = emptyList()
                )
                saveParentList(parent)
            }
            ParentListMode.EDIT -> setParentNameForObserving(name)
        }
    }

    /////////////////****  For definition default name  ****//////////////////
    private tailrec fun generateUniqueDefaultName(defaultName: String): String {
        return if (checkUniqueName(defaultName)) defaultName
        else generateUniqueDefaultName(defaultName.incrementDefaultName())
    }

    private fun checkUniqueName(checkName: String): Boolean {
        val result = parentEditInteractor.getAllParentNames()
            .subscribeOn(Schedulers.io())
            .blockingGet().orEmpty().also { Timber.i("checkUniqueName list: $it") }
        return if (result.isNullOrEmpty()) true else result.contains(checkName).not()
    }

    /////////////////////******************///////////////////////

    // start observing ParentList by name and place it to LiveData
    private fun setParentNameForObserving(parentName: String) {
        disposableBag.add(
            parentEditInteractor.getParentListWithGoals(parentName)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ _data.postValue(it)}, {Timber.e("Parent list not found: ${it.localizedMessage}")})
        )
    }

    // initial all view components for observe
    fun viewCreated() {
        viewState.startListeningData()
        viewState.initComponents()
    }

    //for hiding and showing recycler (there is data or there is no data)
    fun changedStatusData(existence: Boolean) = viewState.changeStateRecycler(existence = existence)

    // saving Parent List to DB and start it observing
    private fun saveParentList(parent: ParentWithListGoals) {
        disposableBag.add(parentEditInteractor.insertParentListGoals(parent)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { setParentNameForObserving(parentName = parent.parentName) },
                { Timber.e("Saving Parent list failed: ${it.localizedMessage}") })
        )
    }

    //Save GoalItem to DB with check parentList for exist
    fun saveNewGoalItem(name: String) {
        if (name.isEmpty()) {
            viewState.showError(R.string.error_input_name_goal)
            return
        }

        val parentId = data.value!!.parentId
        val dateGoal = DateTime.now().millis
        val goalItem = GoalItem(goalDate = dateGoal, goalName = name, parentList = parentId)

        disposableBag.add(
            parentEditInteractor.insertGoal(goalItem)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ({Timber.i("Goal saved") },{Timber.e("Saved goal failed ${it.localizedMessage}")})
        )
    }

    //edit Parent Name with check for unique name in DB
    private fun editParentName(newName: String) {
        val uniqueName = checkUniqueName(checkName = newName)
        if (uniqueName) {
            val parentUpdate = data.value!!.run { copy() }
            disposableBag.add(
                parentEditInteractor.updateParentList(parentUpdate)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({Timber.e("Update parent done")},{Timber.e("Update parent failed: ${it.localizedMessage}")})
            )
        } else viewState.showError(R.string.error_name_already_exist)
    }

    // handling click on recycler and open EditDialog for change Goal's name or delete GoalItem
    fun handleClickToGoalItem(goal:GoalItem, position: Int, type: RecyclerClickType) = when(type) {
        RecyclerClickType.DELETE ->  {
            deleteGoalItem(goal)
            Timber.i("Delete pos: $position")
        }
        RecyclerClickType.EDIT_NAME -> {
            Timber.i("Edit pos: $position")
        }
    }

    //////////////* Deleting block  *////////////////////

    private fun deleteParentList(parent: ParentWithListGoals) {
        disposableBag.add(
            parentEditInteractor.deleteParentList(parent = parent)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({Timber.i("Parent list deleted")},{Timber.e("Deleting Parent list failed: ${it.localizedMessage}")})
        )
    }

    private fun deleteGoalItem(goal: GoalItem) {
        disposableBag.add(
            parentEditInteractor.deleteGoal(goal)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({Timber.i("Goal deleted")}, {Timber.e("Deleted failed: ${it.localizedMessage}")})
        )
    }

    private fun deleteAllGoals() {
        if (data.value != null)
            disposableBag.add(
                parentEditInteractor.deleteAllGoals(parentId = data.value!!.parentId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({Timber.i("All goals deleted")},{Timber.e("Deleted failed: ${it.localizedMessage}")})
            )
    }


    ///////////////////////*********Clear Disposable Bag**********///////////////////

    fun destroyView() {
        data.value?.let { parent -> if (parent.listGoals.isNullOrEmpty()) deleteParentList(parent) }
        disposableBag.clear()
    }
}