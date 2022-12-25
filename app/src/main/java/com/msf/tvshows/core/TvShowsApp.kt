package com.msf.tvshows.core

import android.app.Application
import com.msf.tvshows.di.ShowDI
import com.msf.tvshows.di.TvShowNetworkDI
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class TvShowsApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@TvShowsApp)
            modules(ShowDI.module)
            modules(TvShowNetworkDI.module)
        }
    }
}
