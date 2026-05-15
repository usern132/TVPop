package com.silliconpowerinc.tvpop

import android.app.Application
import com.silliconpowerinc.tvpop.di.AppModule
import org.koin.core.annotation.KoinApplication
import org.koin.plugin.module.dsl.startKoin

@KoinApplication
class TVPopApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin<AppModule>()
    }
}
