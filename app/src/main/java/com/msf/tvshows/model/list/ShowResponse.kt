package com.msf.tvshows.model.list

import com.google.gson.annotations.SerializedName

data class ShowResponse(
    val page: Int,
    val results: List<Show>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)
