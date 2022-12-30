package com.msf.tvshows.model.detail

import com.google.gson.annotations.SerializedName

data class DetailResponse(
    val adult: Boolean,

    @SerializedName("backdrop_path")
    val poster: String,

    @SerializedName("created_by")
    val createdBy: List<CreatedBy>,

    @SerializedName("first_air_date")
    val firstAirDate: String,

    val genres: List<Genre>,

    val homepage: String,

    val id: Int,
    @SerializedName("in_production")
    val inProduction: Boolean,

    val languages: List<String>,

    @SerializedName("last_air_date")
    val lastAirDate: String,

    @SerializedName("last_episode_to_air")
    val last_episode_to_air: LastEpisodeToAir,

    val name: String,

    @SerializedName("number_of_episodes")
    val numberOfEpisodes: Int,

    @SerializedName("number_of_seasons")
    val numberOfSeasons: Int,

    @SerializedName("origin_country")
    val originCountry: List<String>,

    @SerializedName("original_language")
    val originalLanguage: String,

    @SerializedName("original_name")
    val originalName: String,

    val overview: String,

    val popularity: Double,

    @SerializedName("poster_path")
    val posterPath: String,

    val seasons: List<Season>,

    @SerializedName("spoken_languages")
    val spokenLanguages: List<SpokenLanguage>,

    val status: String,

    val tagline: String,

    val type: String,

    @SerializedName("vote_average")
    val voteAverage: Double,

    @SerializedName("vote_count")
    val voteCount: Int
)
