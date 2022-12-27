package com.msf.tvshows.di

import com.msf.tvshows.repository.ShowRepository
import com.msf.tvshows.repository.impl.ShowRepositoryImpl
import org.koin.dsl.module

object TvShowRepositoryDI {
    val modules = module {
        factory<ShowRepository> {
            ShowRepositoryImpl(service = get())
        }
    }
}
