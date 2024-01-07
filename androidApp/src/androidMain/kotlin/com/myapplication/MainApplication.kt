package com.myapplication

import android.app.Application
import modules.dataModules
import modules.viewModelModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin{
            androidContext(this@MainApplication)
            androidLogger()

            modules(viewModelModules, dataModules)
        }
    }
}