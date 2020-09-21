package com.quantumman.randomgoals.app.ui.presenters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.quantumman.randomgoals.R
import com.quantumman.randomgoals.app.interactors.ParentWithGoalsInteractor
import com.quantumman.randomgoals.app.model.IconsGoals
import com.quantumman.randomgoals.app.model.ParentWithListGoals
import com.quantumman.randomgoals.app.ui.views.RandomScreenView
import io.reactivex.rxjava3.disposables.CompositeDisposable
import moxy.MvpPresenter
import timber.log.Timber

class RandomScreenPresenter(private val parentWithGoalsInteractor: ParentWithGoalsInteractor) : MvpPresenter<RandomScreenView>() {

    private val disposableBag = CompositeDisposable()
    private var choseParentList: ParentWithListGoals? = null

    private val _data = MutableLiveData<List<ParentWithListGoals>>()
    val data: LiveData<List<ParentWithListGoals>> = _data

    override fun attachView(view: RandomScreenView?) {
        super.attachView(view)
        disposableBag.add(parentWithGoalsInteractor.getAllParentsWithListGoals()
            .subscribe( {_data.postValue(it)}, {
                viewState.showError(R.string.error_no_items)
                Timber.e("Getting all parents failed: ${it.localizedMessage}")
            } ))
    }

    fun showData() {
        viewState.prepareComponents()
    }

    override fun destroyView(view: RandomScreenView?) {
        disposableBag.clear()
        super.destroyView(view)
    }

    fun clickedOnParentListInSpinner(position: Int) {
        choseParentList = data.value?.get(position)
    }

    fun clickedOnStartRandomGoal() {
        return when (val parent = choseParentList) {
            null -> viewState.showError(R.string.error_not_choosed_list)
            else -> {
                if (parent.listGoals.isNullOrEmpty()) {
                    viewState.showError(R.string.error_no_items)
                    return
                }
                val goal = parent.listGoals.shuffled().first()
                viewState.showRandomResult(goal, IconsGoals.ICONS_LIST[parent.parentIcon].iconName)
            }
        }
    }
}