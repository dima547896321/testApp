package com.loadmovietestapp.app

import android.app.Application
import com.loadmovietestapp.app.di.networkModule
import com.loadmovietestapp.app.di.providers
import com.loadmovietestapp.app.di.repositoriesModule
import com.loadmovietestapp.app.di.viewModelsModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber

class TestApplication : KotlinApplication() {

    companion object {
        lateinit var instance: TestApplication
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}

open class KotlinApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            androidContext(this@KotlinApplication)
            modules(networkModule, viewModelsModule, repositoriesModule, providers)
        }
    }
}