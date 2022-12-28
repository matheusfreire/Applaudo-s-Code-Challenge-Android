package com.msf.tvshows.viewmodel

enum class FilterType(val filterName: String, val presentName: String) {
    TOP_RATED("top_rated", "Top Rated"),
    POPULAR("popular", "Popular"),
    ON_TV("on_the_air", "On Tv"),
    AIRING_TODAY("airing_today", "Airing Today")
}
