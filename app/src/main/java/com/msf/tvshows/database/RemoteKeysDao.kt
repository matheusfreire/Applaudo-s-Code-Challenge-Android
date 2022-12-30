package com.msf.tvshows.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.msf.tvshows.model.list.RemoteKeys

@Dao
interface RemoteKeysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<RemoteKeys>)

    @Query("SELECT * FROM remote_key Where show_id = :id")
    suspend fun getRemoteKeyByShowID(id: Int): RemoteKeys?

    @Query("DELETE FROM remote_key")
    suspend fun clearRemoteKeys()

    @Query("SELECT created_at FROM remote_key Order By created_at DESC LIMIT 1")
    suspend fun getCreationTime(): Long?
}
