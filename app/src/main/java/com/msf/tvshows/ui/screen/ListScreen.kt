package com.msf.tvshows.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.msf.tvshows.model.Show
import com.msf.tvshows.ui.components.ShowCard
import com.msf.tvshows.viewmodel.ShowViewModel
import com.msf.tvshows.viewmodel.UiState
import org.koin.androidx.compose.koinViewModel

@Composable
fun ListScreen(
    modifier: Modifier = Modifier,
    showViewModel: ShowViewModel = koinViewModel(),
    onShowClicked: (Int) -> Unit
) {
    val uiState by showViewModel.uiState.collectAsState()
    when (uiState) {
        is UiState.Loading -> ShowLoading(modifier)
        is UiState.Loaded -> ShowListScreen(shows = (uiState as UiState.Loaded).shows, onShowClicked = onShowClicked)
        else -> ShowLoading(modifier)
    }
}

@Composable
fun ShowLoading(modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        Text(text = "Carregando")
    }
}

@Composable
fun ShowListScreen(
    modifier: Modifier = Modifier,
    shows: List<Show>,
    onShowClicked: (Int) -> Unit
) {
    Box(modifier = modifier) {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = LocalConfiguration.current.screenWidthDp.dp / 2)
        ) {
            items(items = shows) { show ->
                ShowCard(show = show) {
                    onShowClicked.invoke(show.id)
                }
            }
        }
    }
}
