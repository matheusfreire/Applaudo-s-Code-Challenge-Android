package com.msf.tvshows.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.msf.tvshows.R

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ShowCard(title: String, rating: Double = 0.0, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .size(
                width = 156.dp,
                height = 216.dp
            )
            .clip(shape = RoundedCornerShape(size = 8.dp)),
        onClick = { onClick.invoke() },
        elevation = 8.dp
    ) {
        Column {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("https://example.com/image.jpg")
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.ic_placeholder),
                contentDescription = title,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.size(width = 156.dp, height = 136.dp)
            )
            Column(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
            ) {
                Text(
                    text = title,
                    color = colorResource(id = R.color.gray_text),
                    fontSize = 14.sp,
                    lineHeight = 24.sp,
                    textAlign = TextAlign.Right,
                    fontWeight = FontWeight(500)
                )
                Row(modifier = Modifier.padding(top = 4.dp, bottom = 20.dp)) {
                    RatingBar(rating = rating)
                    Spacer(modifier = Modifier.padding(horizontal = 4.dp))
                    Text(
                        text = rating.toString(),
                        color = colorResource(id = R.color.gray_text),
                        fontSize = 14.sp,
                        lineHeight = 20.sp,
                        textAlign = TextAlign.Right,
                        fontWeight = FontWeight(400)
                    )
                }
            }
        }
    }
}

@Composable
@Preview
fun ShowCardPreview() {
    ShowCard(title = "Test", rating = 4.5) {}
}
