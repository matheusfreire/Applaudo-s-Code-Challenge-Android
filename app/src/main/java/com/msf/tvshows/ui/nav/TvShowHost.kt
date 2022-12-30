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
import com.msf.tvshows.ui.screen.SplashScreen
import com.msf.tvshows.ui.screen.Screen

@Composable
fun TvShowHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.path,
        modifier = modifier
    ) {
        composable(route = Screen.Splash.path) {
            SplashScreen {
                navController.navigate(
                    Screen.List.path
                )
            }
        }
        composable(route = Screen.List.path) {
            ListScreen(
                onShowClicked = {
                    navController.navigate(
                        Screen.ShowDetail.path.replace(
                            oldValue = "{ID}",
                            newValue = "$it"
                        )
                    )
                }
            )
        }
        composable(
            route = Screen.ShowDetail.path,
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
