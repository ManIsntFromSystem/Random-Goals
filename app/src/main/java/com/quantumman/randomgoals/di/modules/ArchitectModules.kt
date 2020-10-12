package com.quantumman.randomgoals.di.modules

import com.quantumman.randomgoals.app.interactors.EditParentListWithGoalsInteractor
import com.quantumman.randomgoals.app.interactors.EditParentListWithGoalsInteractorImpl
import com.quantumman.randomgoals.app.interactors.ParentWithGoalsInteractor
import com.quantumman.randomgoals.app.interactors.ParentWithGoalsInteractorImpl
import com.quantumman.randomgoals.app.ui.presenters.*
import com.quantumman.randomgoals.data.repositories.ParentWithGoalsRepository
import com.quantumman.randomgoals.data.repositories.ParentWithGoalsRepositoryImpl
import org.koin.dsl.module

val architectModule = module {
    single <ParentWithGoalsRepository> {
        ParentWithGoalsRepositoryImpl(get(), get(), get())
    }
    factory <ParentWithGoalsInteractor> {
        ParentWithGoalsInteractorImpl(get())
    }
    factory <EditParentListWithGoalsInteractor> {
        EditParentListWithGoalsInteractorImpl(get())
    }
    single { AllParentListsPresenter(get(), get()) }
    single { RandomScreenPresenter(get(), get()) }
    single { EditParentListPresenter(get()) }
    single { MainActivityPresenter(get()) }
    single { SplashScreenPresenter(get()) }
}