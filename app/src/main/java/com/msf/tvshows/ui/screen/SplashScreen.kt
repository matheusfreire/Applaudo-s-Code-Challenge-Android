package com.msf.tvshows.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.msf.tvshows.R

@Composable
fun SplashScreen(
    onFinishLoad: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        colorResource(id = R.color.primary),
                        colorResource(id = R.color.secondary)
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(id = R.drawable.ic_splash_bg),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(
                    width = 213.dp,
                    height = 64.dp
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            CircularProgressIndicator(
                modifier = Modifier.size(
                    width = 80.dp,
                    height = 80.dp
                ),
                color = colorResource(id = R.color.white)
            )
        }
        onFinishLoad.invoke()
    }
}

@Composable
@Preview
fun SplashPreview() {
    SplashScreen {}
}
