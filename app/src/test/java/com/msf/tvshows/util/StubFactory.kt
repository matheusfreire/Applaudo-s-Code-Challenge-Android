package com.msf.tvshows.util

import com.msf.tvshows.model.detail.CreatedBy
import com.msf.tvshows.model.detail.DetailResponse
import com.msf.tvshows.model.detail.Genre
import com.msf.tvshows.model.detail.LastEpisodeToAir
import com.msf.tvshows.model.detail.Season
import com.msf.tvshows.model.detail.SpokenLanguage
import com.msf.tvshows.model.list.Show
import com.msf.tvshows.model.list.ShowResponse

object StubFactory {

    val showResponse = ShowResponse(
        page = 1,
        shows = listOf(
            Show(
                backdropPath = "",
                firstAirDate = "",
                genreIds = listOf(),
                id = 1,
                name = "Show Response Name",
                originCountry = listOf(),
                originalLanguage = "",
                originalName = "",
                overview = "",
                popularity = 0.0,
                posterPath = "",
                voteAverage = 0.0,
                voteCount = 1
            )
        ),
        totalPages = 1,
        totalResults = 1
    )
    val detailResponse = DetailResponse(
        adult = false,
        backdrop_path = "",
        created_by = listOf(
            CreatedBy(
                credit_id = "",
                gender = 1,
                id = 1,
                name = "",
                profile_path = ""
            )
        ),
        first_air_date = "",
        genres = listOf(
            Genre(
                id = 1,
                name = ""
            )
        ),
        homepage = "",
        id = 1,
        in_production = true,
        languages = listOf("en", "pt"),
        last_air_date = "",
        last_episode_to_air = LastEpisodeToAir(
            air_date = "",
            episode_number = 1,
            id = 1,
            name = "",
            overview = "",
            production_code = "",
            runtime = 1,
            season_number = 1,
            show_id = 1,
            still_path = "",
            vote_average = 0.0,
            vote_count = 1
        ),
        name = "",
        number_of_episodes = 1,
        number_of_seasons = 1,
        origin_country = listOf("USA", "BR"),
        original_language = "en",
        original_name = "",
        overview = "",
        popularity = 0.0,
        poster_path = "",
        seasons = listOf(
            Season(
                air_date = "",
                episode_count = 3,
                id = 1,
                name = "",
                overview = "",
                poster_path = "",
                season_number = 1
            )
        ),
        spoken_languages = listOf(
            SpokenLanguage(
                english_name = "",
                iso_639_1 = "",
                name = ""
            )
        ),
        status = "",
        tagline = "",
        type = "",
        vote_average = 0.0,
        vote_count = 1
    )
}
