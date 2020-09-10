package com.quantumman.randomgoals.di.module

import com.quantumman.randomgoals.app.interactors.ParentWithGoalsInteractor
import com.quantumman.randomgoals.app.interactors.ParentWithGoalsInteractorImpl
import com.quantumman.randomgoals.app.ui.presenters.AllParentListsPresenter
import com.quantumman.randomgoals.app.ui.presenters.EditParentListPresenter
import com.quantumman.randomgoals.app.ui.presenters.RandomScreenPresenter
import com.quantumman.randomgoals.repositories.ParentWithGoalsRepository
import com.quantumman.randomgoals.repositories.ParentWithGoalsRepositoryImpl
import org.koin.dsl.module

val architectModule = module {
    factory <ParentWithGoalsRepository> {
        ParentWithGoalsRepositoryImpl(get())
    }
    factory <ParentWithGoalsInteractor> {
        ParentWithGoalsInteractorImpl(get())
    }
    single { AllParentListsPresenter(get()) }
    single { RandomScreenPresenter(get()) }
    single { EditParentListPresenter(get()) }
}