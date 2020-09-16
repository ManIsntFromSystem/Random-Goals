package com.quantumman.randomgoals.app.ui.presenters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.quantumman.randomgoals.app.interactors.ParentWithGoalsInteractor
import com.quantumman.randomgoals.app.model.ParentWithListGoals
import com.quantumman.randomgoals.app.ui.views.AllParentListsView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import moxy.InjectViewState
import moxy.MvpPresenter
import timber.log.Timber

@InjectViewState
class AllParentListsPresenter(private val parentWithGoalsInteractor: ParentWithGoalsInteractor) : MvpPresenter<AllParentListsView>() {

    private val _data = MutableLiveData<List<ParentWithListGoals>>()
    val data: LiveData<List<ParentWithListGoals>> = _data

    init {
        parentWithGoalsInteractor.getAllParentsWithListGoals()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe( { _data.postValue(it) }, {
                viewState.showError("No items")
                Timber.e(it)
            } )
    }

    fun deleteParentList() { /*parentWithGoalsInteractor(1)*/ }

    fun showData() { viewState.initShowingData() }
}