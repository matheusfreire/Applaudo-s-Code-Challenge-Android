package com.msf.tvshows.ui.screen // ktlint-disable filename

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.msf.tvshows.R
import com.msf.tvshows.model.detail.DetailResponse
import com.msf.tvshows.ui.components.SeasonCard
import com.msf.tvshows.viewmodel.DetailViewModel
import com.msf.tvshows.viewmodel.UiState
import org.koin.androidx.compose.koinViewModel

@Composable
fun ShowDetail(
    id: Long,
    modifier: Modifier = Modifier,
    detailViewModel: DetailViewModel = koinViewModel()
) {
    val uiState by detailViewModel.uiState.collectAsState()
    detailViewModel.getDetail(id)
    when (uiState) {
        is UiState.Loading -> ShowLoading(modifier)
        is UiState.Loaded<*> -> BodyDetail((uiState as UiState.Loaded<*>).value as DetailResponse)
        else -> ShowLoading(modifier)
    }
}

@Composable
fun BodyDetail(detailResponse: DetailResponse) {
    val scrollState = rememberScrollState()
    Box(
        modifier = Modifier.scrollable(
            state = scrollState,
            orientation = Orientation.Vertical
        ).padding(
            top = 24.dp,
            bottom = 32.dp,
            start = 16.dp,
            end = 16.dp
        )
    ) {
        Column {
            Text(
                text = "Summary",
                color = colorResource(id = R.color.primary),
                fontWeight = FontWeight(500),
                fontSize = 20.sp,
                lineHeight = 23.44.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = detailResponse.overview,
                color = colorResource(id = R.color.text),
                fontWeight = FontWeight(400),
                fontSize = 14.sp,
                lineHeight = 20.sp
            )
            LazyColumn {
                items(detailResponse.seasons) { season ->
                    SeasonCard(season) {}
                }
            }
        }
    }
}
