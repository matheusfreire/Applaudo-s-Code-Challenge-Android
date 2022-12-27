package com.msf.tvshows.ui.nav

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.msf.tvshows.R
import com.msf.tvshows.ui.screen.ListScreen
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
            startDestination = TvShowScreen.LIST.name,
            modifier = modifier.padding(innerPadding)
        ) {
            composable(route = TvShowScreen.LIST.name) {
                ListScreen(
                    onShowClicked = {
                        navController.navigate(TvShowScreen.SHOW_DETAIL.name)
                    }
                )
            }
        }
    }
}
