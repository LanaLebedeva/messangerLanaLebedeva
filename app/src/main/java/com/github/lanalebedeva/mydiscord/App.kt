package com.github.lanalebedeva.mydiscord

import android.app.Application
import com.github.lanalebedeva.mydiscord.di.ApplicationComponent
import com.github.lanalebedeva.mydiscord.di.ApplicationModule
import com.github.lanalebedeva.mydiscord.di.DaggerApplicationComponent

class App:Application() {
    lateinit var appComponent: ApplicationComponent
        private set

    override fun onCreate() {
        appComponent = DaggerApplicationComponent
            .builder()
            .applicationModule(ApplicationModule(applicationContext))
            .build()
        super.onCreate()
    }
}
