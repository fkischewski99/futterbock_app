package modules

import org.koin.dsl.module

import view.event.homescreen.ViewModelEventOverview

val viewModelModules = module {
    single { ViewModelEventOverview(get()) }
}