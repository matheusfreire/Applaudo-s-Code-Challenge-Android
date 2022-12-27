package com.msf.tvshows.core

import android.app.Application
import com.msf.tvshows.di.ShowDI
import com.msf.tvshows.di.TvShowNetworkDI
import com.msf.tvshows.di.TvShowRepositoryDI
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class TvShowsApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@TvShowsApplication)
            modules(ShowDI.modules)
            modules(TvShowRepositoryDI.modules)
            modules(TvShowNetworkDI.modules)
        }
    }
}
