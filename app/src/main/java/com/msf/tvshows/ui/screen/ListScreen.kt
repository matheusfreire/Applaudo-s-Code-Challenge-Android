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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.google.accompanist.flowlayout.FlowRow
import com.msf.tvshows.R
import com.msf.tvshows.extensions.isInternetAvailable
import com.msf.tvshows.model.list.Show
import com.msf.tvshows.ui.components.FilterChip
import com.msf.tvshows.ui.components.Loading
import com.msf.tvshows.ui.components.Message
import com.msf.tvshows.ui.components.ShowCard
import com.msf.tvshows.viewmodel.FilterType
import com.msf.tvshows.viewmodel.ShowViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun ListScreen(
    modifier: Modifier = Modifier,
    showViewModel: ShowViewModel = koinViewModel(),
    onShowClicked: (Int) -> Unit
) {
    var filterSelected by rememberSaveable {
        mutableStateOf(FilterType.TOP_RATED)
    }
    val uiState = showViewModel.fetchShowList(filterSelected).collectAsLazyPagingItems()

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
                            isSelected = filterSelected == filter,
                            text = filter.presentName,
                            onClick = { filterSelected = filter }
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            if (LocalContext.current.isInternetAvailable()) {
                ShowListScreen(shows = uiState, onShowClicked = onShowClicked)
            } else {
                Message("No connection detected, please try again with connection")
            }
        }
    }
}

@Composable
fun ShowListScreen(
    shows: LazyPagingItems<Show>,
    onShowClicked: (Int) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(12.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        val loadState = shows.loadState.mediator
        items(
            items = shows.itemSnapshotList.items
        ) { show ->
            ShowCard(show = show) {
                onShowClicked.invoke(show.id)
            }
        }
        if (loadState?.refresh == LoadState.Loading) {
            BoxLoading()
        }
        if (loadState?.append == LoadState.Loading) {
           LoadingItem()
        }
        if (loadState?.refresh is LoadState.Error || loadState?.append is LoadState.Error) {
            ErrorView(loadState, shows)
        }
    }
}

private fun LazyGridScope.ErrorView(loadState: LoadStates, shows: LazyPagingItems<Show>) {
    item {
        val isPaginatingError = (loadState.append is LoadState.Error) || shows.itemCount > 1
        val error = if (loadState.append is LoadState.Error) {
            (loadState.append as LoadState.Error).error
        } else {
            (loadState.refresh as LoadState.Error).error
        }
        val modifier = if (isPaginatingError) {
            Modifier.padding(8.dp)
        } else {
            Modifier.fillMaxSize()
        }
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            if (!isPaginatingError) {
                Icon(
                    modifier = Modifier
                        .size(64.dp),
                    imageVector = Icons.Rounded.Warning, contentDescription = null
                )
            }
            Text(
                modifier = Modifier
                    .padding(8.dp),
                text = error.message ?: error.toString(),
                textAlign = TextAlign.Center,
            )
            Button(
                onClick = {
                    shows.refresh()
                },
                content = {
                    Text(text = "Refresh")
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.primary,
                    contentColor = Color.White,
                )
            )
        }
    }
}

private fun LazyGridScope.BoxLoading() {
    item {
        Loading()
    }
}

private fun LazyGridScope.LoadingItem() {
    item {
        CircularProgressIndicator(modifier = Modifier.padding(16.dp))
    }
}
