package com.moviedb

import android.app.Application
import com.moviedb.di.appModule
import com.moviedb.di.networkModule
import com.moviedb.di.repositoryModule
import com.moviedb.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApplication)
            modules(appModule, networkModule, viewModelModule, repositoryModule)
        }
    }
}