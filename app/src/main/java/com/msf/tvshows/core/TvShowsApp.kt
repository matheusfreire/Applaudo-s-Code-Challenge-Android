package com.msf.tvshows.core

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class TvShowsApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@TvShowsApp)
//            modules(ItunesSearchDi.module)
        }
    }
}
