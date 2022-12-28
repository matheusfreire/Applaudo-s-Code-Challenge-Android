package com.msf.tvshows.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.flowlayout.FlowRow
import com.msf.tvshows.R
import com.msf.tvshows.model.list.Show
import com.msf.tvshows.ui.components.FilterChip
import com.msf.tvshows.ui.components.Loading
import com.msf.tvshows.ui.components.ShowCard
import com.msf.tvshows.viewmodel.FilterType
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
    Scaffold(
        topBar = {
            androidx.compose.material.TopAppBar(
                title = {
                    Text(
                        text = "TV Shows",
                        modifier = Modifier.padding(vertical = 16.dp),
                        color = colorResource(id = R.color.white)
                    )
                },
                backgroundColor = colorResource(id = R.color.primary)
            )
        }
    ) { innerPadding ->
        Column(modifier = modifier.padding(innerPadding)) {
            FlowRow(
                modifier = Modifier
                    .height(32.dp)
                    .fillMaxWidth()
                    .padding(vertical = 24.dp)
                    .padding(start = 16.dp)
            ) {
                FilterType.values().forEach { filter ->
                    Box(modifier = Modifier.padding(end = 8.dp)) {
                        FilterChip(
                            isSelected = showViewModel.selectedFilter == filter,
                            text = filter.presentName,
                            onClick = { showViewModel.fetchShowList(filter) }
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            when (uiState) {
                is UiState.Loading -> Loading()
                is UiState.Loaded<*> -> ShowListScreen(
                    shows = (uiState as UiState.Loaded<*>).value as List<Show>,
                    onShowClicked = onShowClicked
                ) { showViewModel.fetchShowList(filter = showViewModel.selectedFilter, 2, false) }
                else -> Loading()
            }
        }
    }
}

@Composable
fun ShowListScreen(
    shows: List<Show>,
    onShowClicked: (Int) -> Unit,
    onReachEnd: () -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(12.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(items = shows) { show ->
            ShowCard(show = show) {
                onShowClicked.invoke(show.id)
            }
        }
    }
}
