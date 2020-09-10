package com.quantumman.randomgoals.app.ui.presenters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.quantumman.randomgoals.app.interactors.ParentWithGoalsInteractor
import com.quantumman.randomgoals.app.model.ParentWithListGoals
import com.quantumman.randomgoals.app.ui.views.EditParentListsView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import moxy.InjectViewState
import moxy.MvpPresenter
import timber.log.Timber

@InjectViewState
class EditParentListPresenter(
    private val parentWithGoalsInteractor: ParentWithGoalsInteractor
) : MvpPresenter<EditParentListsView>() {

    private val _data = MutableLiveData<ParentWithListGoals>()
    val data: LiveData<ParentWithListGoals> = _data

    fun showData() { viewState.initShowingData() }
}