package com.quantumman.randomgoals.app.ui.presenters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.quantumman.randomgoals.app.interactors.ParentWithGoalsInteractor
import com.quantumman.randomgoals.app.model.ParentWithListGoals
import com.quantumman.randomgoals.app.ui.views.RandomScreenView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import moxy.MvpPresenter
import timber.log.Timber

class RandomScreenPresenter(private val parentWithGoalsInteractor: ParentWithGoalsInteractor) : MvpPresenter<RandomScreenView>() {

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

    fun showData() { viewState.prepareComponents() }
}