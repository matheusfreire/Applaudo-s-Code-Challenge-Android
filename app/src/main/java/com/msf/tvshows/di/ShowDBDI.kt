package com.msf.tvshows.di

import android.content.Context
import androidx.room.Room
import com.msf.tvshows.database.ShowDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

object ShowDBDI {
    val modules = module {
        single {
            provideMovieDatabase(androidApplication().applicationContext)
        }
    }
}

fun provideMovieDatabase(context: Context): ShowDatabase =
    Room
        .databaseBuilder(context, ShowDatabase::class.java, "movies_database")
        .build()
