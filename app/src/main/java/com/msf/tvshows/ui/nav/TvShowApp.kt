package com.msf.tvshows.ui.nav

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.msf.tvshows.R
import com.msf.tvshows.ui.screen.ListScreen
import com.msf.tvshows.ui.screen.ShowDetail
import com.msf.tvshows.ui.screen.TvShowScreen

@Composable
fun TvShowHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    Scaffold(
        topBar = {
            TopAppBar(
                content = {
                    Text(
                        text = "TV Shows",
                        modifier = Modifier.padding(vertical = 16.dp),
                        color = colorResource(id = R.color.white)
                    )
                }
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = TvShowScreen.LIST.path,
            modifier = modifier.padding(innerPadding)
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
                    ShowDetail(it)
                }
            }
        }
    }
}
