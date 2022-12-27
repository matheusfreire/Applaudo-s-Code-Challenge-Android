package com.msf.tvshows.ui.screen

import androidx.annotation.StringRes
import com.msf.tvshows.R

enum class TvShowScreen(@StringRes val title: Int, val path: String) {
    LIST(title = R.string.app_name, "LIST"),
    SHOW_DETAIL(title = R.string.detail_screen, "DETAIL/{ID}"),
    SEASON_EPISODE(title = R.string.app_name, "SEASON/ID"),
}
