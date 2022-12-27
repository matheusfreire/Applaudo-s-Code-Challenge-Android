package com.msf.tvshows.ui.screen

import androidx.annotation.StringRes
import com.msf.tvshows.R

enum class TvShowScreen(@StringRes val title: Int) {
    LIST(title = R.string.app_name),
    SHOW_DETAIL(title = R.string.app_name),
    SEASON_EPISODE(title = R.string.app_name),
}
