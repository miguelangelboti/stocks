package com.miguelangelboti.stocks

import android.app.Application
import timber.log.Timber
import timber.log.Timber.DebugTree

class Application : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(DebugTree())
    }
}
