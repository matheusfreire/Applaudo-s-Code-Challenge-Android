package com.msf.tvshows.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.msf.tvshows.R

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SeasonCard(numberSeason: Int, detail: String, numberEpisodes: Int = 1, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(size = 8.dp)),
        onClick = { onClick.invoke() },
        elevation = 8.dp
    ) {
        Row {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("https://example.com/image.jpg")
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.ic_placeholder),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.size(width = 112.dp, height = 147.dp)
            )
            Column(modifier = Modifier.padding(all = 16.dp)) {
                Text(
                    text = stringResource(id = R.string.season_title, numberSeason),
                    fontSize = 20.sp,
                    fontWeight = FontWeight(500),
                    lineHeight = 23.sp,
                    color = colorResource(id = R.color.text)
                )
                Spacer(modifier = Modifier.padding(top = 8.dp))
                Text(
                    text = stringResource(id = R.string.episode, numberEpisodes),
                    fontSize = 12.sp,
                    fontWeight = FontWeight(600),
                    lineHeight = 15.sp,
                    color = colorResource(id = R.color.primary)
                )
                Spacer(modifier = Modifier.padding(top = 8.dp))
                Text(
                    text = detail,
                    fontSize = 14.sp,
                    fontWeight = FontWeight(400),
                    lineHeight = 20.sp,
                    color = colorResource(id = R.color.text)
                )
            }
        }
    }
}

@Preview
@Composable
fun SeasonCardPreview() {
    SeasonCard(
        numberSeason = 1,
        detail = "As the rest of the team face their worst fears, Noodle Burger Boy.",
        numberEpisodes = 5
    ) {}
}
