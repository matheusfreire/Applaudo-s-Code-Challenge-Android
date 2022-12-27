package com.msf.tvshows.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.msf.tvshows.BuildConfig
import com.msf.tvshows.R
import com.msf.tvshows.model.list.Show

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ShowCard(show: Show, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .size(height = 216.dp, width = 136.dp)
            .clip(shape = RoundedCornerShape(size = 8.dp)),
        onClick = { onClick.invoke() },
        elevation = 16.dp
    ) {
        Column {
            SubcomposeAsyncImage(
                modifier = Modifier.height(height = 136.dp).fillMaxWidth(),
                model = ImageRequest.Builder(LocalContext.current)
                    .data("${BuildConfig.IMAGE_URL}${show.backdropPath}")
                    .crossfade(true)
                    .build(),
                contentDescription = show.name,
                loading = {
                    CircularProgressIndicator()
                }
            )
            Column(
                modifier = Modifier
                    .height(height = 80.dp)
                    .fillMaxWidth()
                    .padding(all = 16.dp)
            ) {
                Text(
                    text = show.name,
                    color = colorResource(id = R.color.gray_text),
                    fontSize = 14.sp,
                    lineHeight = 24.sp,
                    textAlign = TextAlign.Right,
                    fontWeight = FontWeight(500),
                    modifier = Modifier.height(24.dp)
                )
                Spacer(modifier = Modifier.height(8.dp).fillMaxWidth())
                Row(modifier = Modifier.height(24.dp)) {
                    RatingBar(rating = 5.0, modifier = Modifier.size(width = 88.dp, height = 16.dp))
                    Text(
                        text = 5.0.toString(),
                        color = colorResource(id = R.color.gray_text),
                        fontSize = 14.sp,
                        lineHeight = 20.sp,
                        textAlign = TextAlign.Right,
                        fontWeight = FontWeight(400),
                        modifier = Modifier.size(width = 24.dp, height = 16.dp)
                    )
                }
            }
        }
    }
}

@Composable
@Preview
fun ShowCardPreview() {
    ShowCard(
        show = Show(
            backdropPath = "",
            firstAirDate = "",
            genreIds = listOf(),
            id = 1,
            name = "Name",
            originCountry = listOf(),
            originalLanguage = "",
            originalName = "Nmae",
            overview = "",
            10.0,
            "",
            5.0,
            1
        )
    ) {}
}
