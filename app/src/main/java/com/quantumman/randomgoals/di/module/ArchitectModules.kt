package com.quantumman.randomgoals.di.module

import com.quantumman.randomgoals.app.interactors.EditParentListWithGoalsInteractor
import com.quantumman.randomgoals.app.interactors.EditParentListWithGoalsInteractorImpl
import com.quantumman.randomgoals.app.interactors.ParentWithGoalsInteractor
import com.quantumman.randomgoals.app.interactors.ParentWithGoalsInteractorImpl
import com.quantumman.randomgoals.app.ui.presenters.AllParentListsPresenter
import com.quantumman.randomgoals.app.ui.presenters.EditParentListPresenter
import com.quantumman.randomgoals.app.ui.presenters.RandomScreenPresenter
import com.quantumman.randomgoals.data.repositories.ParentWithGoalsRepository
import com.quantumman.randomgoals.data.repositories.ParentWithGoalsRepositoryImpl
import org.koin.dsl.module

val architectModule = module {
    factory <ParentWithGoalsRepository> {
        ParentWithGoalsRepositoryImpl(get(), get(), get())
    }
    factory <ParentWithGoalsInteractor> {
        ParentWithGoalsInteractorImpl(get())
    }
    factory <EditParentListWithGoalsInteractor> {
        EditParentListWithGoalsInteractorImpl(get())
    }
    factory { AllParentListsPresenter(get()) }
    factory { RandomScreenPresenter(get()) }
    factory { EditParentListPresenter(get()) }
}