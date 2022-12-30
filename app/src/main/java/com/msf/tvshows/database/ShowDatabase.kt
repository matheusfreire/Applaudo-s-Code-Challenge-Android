package com.msf.tvshows.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.msf.tvshows.model.list.RemoteKeys
import com.msf.tvshows.model.list.Show

@Database(
    entities = [Show::class, RemoteKeys::class],
    version = 1
)
abstract class ShowDatabase : RoomDatabase() {
    abstract fun getShowsDao(): ShowsDao
    abstract fun getRemoteKeysDao(): RemoteKeysDao
}
