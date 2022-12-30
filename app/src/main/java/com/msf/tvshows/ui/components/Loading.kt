package com.msf.tvshows.ui.components

import androidx.annotation.ColorRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import com.msf.tvshows.R

@Composable
fun Loading(@ColorRes color: Int = R.color.primary) {
    Box(modifier = Modifier.fillMaxHeight().fillMaxWidth()) {
        CircularProgressIndicator(
            modifier = Modifier.align(Alignment.Center),
            color = colorResource(id = color)
        )
    }
}

@Composable
@Preview
fun LoadingPreviewWithDefault() {
    Loading()
}

@Composable
@Preview
fun LoadingPreviewWithColor() {
    Loading(color = R.color.black)
}
