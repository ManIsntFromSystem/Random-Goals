package com.quantumman.randomgoals.app.ui.presenters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.quantumman.randomgoals.R
import com.quantumman.randomgoals.app.interactors.ParentWithGoalsInteractor
import com.quantumman.randomgoals.app.model.ParentWithListGoals
import com.quantumman.randomgoals.app.ui.views.AllParentListsView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import moxy.InjectViewState
import moxy.MvpPresenter
import timber.log.Timber

@InjectViewState
class AllParentListsPresenter(private val parentWithGoalsInteractor: ParentWithGoalsInteractor) : MvpPresenter<AllParentListsView>() {

    // For clear disposables
    private val disposableBag = CompositeDisposable()
    //for update UI
    private val _data = MutableLiveData<List<ParentWithListGoals>>()
    val data: LiveData<List<ParentWithListGoals>> = _data

    init {
        disposableBag.add(parentWithGoalsInteractor.getAllParentsWithListGoals()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe( { _data.postValue(it) }, {
                viewState.showError(R.string.error_no_items)
                Timber.e(it)
            } )
        )
    }

    fun swapParentList(parent: ParentWithListGoals) {

    }

    // handling click on recycler and open EditDialog for change Goal's name or delete GoalItem
    fun handleClickToParentItem(parent: ParentWithListGoals, position: Int) {
            deleteParentList(parent)
            Timber.i("Delete pos: $position")
    }

    private fun deleteAllGoals() {
        if (data.value != null)
            disposableBag.add(parentWithGoalsInteractor.deleteAllParentLists()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({Timber.i("All parent lists deleted")},{Timber.e("Deleted failed: ${it.localizedMessage}")})
            )
    }

    private fun deleteParentList(parent: ParentWithListGoals) {
        disposableBag.add(parentWithGoalsInteractor.deleteParentList(parent)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({Timber.i("Parent list deleted")},{Timber.e("Deleted failed: ${it.localizedMessage}")})
        )
    }

    fun viewCreated() { viewState.initComponents() }

    ///////////////////////*********Clear Disposable Bag**********///////////////////

    fun destroyView() {
        disposableBag.clear()
    }
}