package com.msf.tvshows.repository

import com.msf.tvshows.model.detail.DetailResponse
import com.msf.tvshows.model.list.ShowResponse
import com.msf.tvshows.network.ResultWrapper

interface ShowRepository {
    suspend fun fetchShowList(filter: String, page: Int = 1): ResultWrapper<ShowResponse>
    suspend fun getShowDetail(id: Long): ResultWrapper<DetailResponse>
}
