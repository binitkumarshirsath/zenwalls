package com.binit.zenwalls

import android.app.Application
import com.binit.zenwalls.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class ZenWallsApp :Application(){
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@ZenWallsApp)
            androidLogger()
            modules(appModule)
        }
    }
}