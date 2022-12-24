package com.msf.tvshows.network

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TvShowService {

    @GET("/{id}")
    fun callDetail(@Path("id") code: Long, @Query("api_key") apiKey: String?)
}
