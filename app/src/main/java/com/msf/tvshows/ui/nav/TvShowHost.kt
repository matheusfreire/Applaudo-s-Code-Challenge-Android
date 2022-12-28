package com.msf.tvshows.ui.nav

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.msf.tvshows.ui.screen.ListScreen
import com.msf.tvshows.ui.screen.ShowDetail
import com.msf.tvshows.ui.screen.TvShowScreen

@Composable
fun TvShowHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = TvShowScreen.LIST.path,
        modifier = modifier
    ) {
        composable(route = TvShowScreen.LIST.path) {
            ListScreen(
                onShowClicked = {
                    navController.navigate(
                        TvShowScreen.SHOW_DETAIL.path.replace(
                            oldValue = "{ID}",
                            newValue = "$it"
                        )
                    )
                }
            )
        }
        composable(
            route = TvShowScreen.SHOW_DETAIL.path,
            arguments = listOf(
                navArgument("ID") {
                    defaultValue = 0
                    type = NavType.LongType
                }
            )
        ) { navBackEntry ->
            val showId = navBackEntry.arguments?.getLong("ID")
            showId?.let {
                ShowDetail(it, navController)
            }
        }
    }
}
