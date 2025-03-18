package com.christopher_two.squid_game_mobile

import android.app.Application
import com.christopher_two.api.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.component.KoinComponent
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application(), KoinComponent {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App.applicationContext)
            androidLogger(Level.DEBUG)
            modules(
                viewModelModule
            )
        }
    }
}