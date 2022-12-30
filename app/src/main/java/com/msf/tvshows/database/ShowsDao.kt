package com.msf.tvshows.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.msf.tvshows.model.list.Show

@Dao
interface ShowsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies: List<Show>)

    @Query("SELECT * FROM shows")
    fun getShows(): PagingSource<Int, Show>

    @Query("DELETE FROM shows")
    suspend fun clearAllShows()
}
