package com.msf.tvshows.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Chip
import androidx.compose.material.ChipDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.msf.tvshows.R

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FilterChip(
    isSelected: Boolean,
    text: String,
    onClick: (Boolean) -> Unit
) {
    val selected by remember {
        mutableStateOf(false)
    }
    Chip(
        border = BorderStroke(
            width = 1.dp,
            color = colorResource(id = if (isSelected) R.color.primary else R.color.border_gray)
        ),
        onClick = { onClick(selected) },
        colors = ChipDefaults.chipColors(
            backgroundColor = colorResource(id = if (isSelected) R.color.primary else R.color.gray_background),
            contentColor = colorResource(id = R.color.black)
        )

    ) {
        Text(
            text = text,
            color = colorResource(id = if (isSelected) R.color.white else R.color.gray_text),
            fontWeight = FontWeight(500),
            fontSize = 14.sp,
            lineHeight = 24.sp
        )
    }
}

@Preview
@Composable
fun ChipPreview() {
    Column {
        FilterChip(isSelected = false, text = "Option", onClick = {})
        FilterChip(isSelected = true, text = "Option", onClick = {})
    }
}
