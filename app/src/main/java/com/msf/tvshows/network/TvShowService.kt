package com.msf.tvshows.network

import com.msf.tvshows.model.detail.DetailResponse
import com.msf.tvshows.model.list.ShowResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TvShowService {

    @GET("{id}")
    suspend fun callDetail(@Path("id") code: Long, @Query("api_key") apiKey: String): DetailResponse

    @GET("{filter}?language=en-US")
    suspend fun callShowsFiltered(
        @Path("filter") filer: String,
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): ShowResponse
}
