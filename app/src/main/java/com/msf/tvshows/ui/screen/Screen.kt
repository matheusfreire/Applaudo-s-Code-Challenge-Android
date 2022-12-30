package com.msf.tvshows.ui.screen

import androidx.annotation.StringRes
import com.msf.tvshows.R

sealed class Screen(@StringRes val title: Int, val path: String) {
    object Splash : Screen(title = R.string.app_name, path = "splash")
    object List : Screen(title = R.string.app_name, "LIST")
    object ShowDetail : Screen(title = R.string.detail_screen, "DETAIL/{ID}")
}
