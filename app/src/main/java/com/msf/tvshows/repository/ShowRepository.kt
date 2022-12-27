package com.msf.tvshows.repository

import com.msf.tvshows.model.ShowResponse
import com.msf.tvshows.network.ResultWrapper

interface ShowRepository {
    suspend fun fetchShowList(filter: String): ResultWrapper<ShowResponse>
    suspend fun getShowDetail(id: String)
}
