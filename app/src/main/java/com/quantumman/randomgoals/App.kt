package com.quantumman.randomgoals

import android.app.Application
import com.quantumman.randomgoals.di.module.architectModule
import com.quantumman.randomgoals.di.module.navigateModule
import com.quantumman.randomgoals.di.module.roomModule
import net.danlew.android.joda.JodaTimeAndroid
import org.koin.android.ext.android.get
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
        initLibraries()
    }

    private fun initKoin() {
        startKoin {
            androidLogger(level = Level.DEBUG)
            androidContext(this@App)
            modules(roomModule, architectModule, navigateModule)
        }
    }

    private fun initLibraries() {
        val tree = Timber.DebugTree()
        Timber.plant(tree)
        JodaTimeAndroid.init(get())
    }
}