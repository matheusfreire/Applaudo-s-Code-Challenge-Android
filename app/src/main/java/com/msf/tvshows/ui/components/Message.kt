package com.msf.tvshows.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.msf.tvshows.R

@Composable
fun Message(message: String, isError: Boolean = false) {
    Box(
        modifier = Modifier.fillMaxHeight().fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            if (isError) {
                Image(
                    painter = painterResource(id = R.drawable.ic_circle_x),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.size(
                        width = 213.dp,
                        height = 64.dp
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
            Text(
                text = message,
                color = colorResource(id = R.color.text),
                fontWeight = FontWeight(500),
                fontSize = 15.sp,
                lineHeight = 24.sp
            )
        }
    }
}

@Composable
@Preview
fun MessagePreview() {
    Message(message = "No network")
}

@Composable
@Preview
fun MessagePreviewWithIcon() {
    Message(message = "No network", true)
}
