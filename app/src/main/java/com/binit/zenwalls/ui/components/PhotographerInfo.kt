package com.binit.zenwalls.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import coil3.compose.AsyncImage
import com.binit.zenwalls.domain.model.UnsplashImage
import com.binit.zenwalls.ui.theme.Secondary

@Composable
fun PhotographerInfo(image:UnsplashImage,modifier: Modifier = Modifier) {
    AsyncImage(
        model = image.photographerProfileImgUrl,
        contentDescription = "photographer",
        modifier = modifier
            .padding(start = 10.dp, end = 20.dp)
            .size(40.dp)
            .clip(RoundedCornerShape(50))
    )
    Text(
        text = image.photographerName?.replaceFirstChar { it.uppercase() } ?: "Anonymous",
        color = Secondary,
        maxLines = 1
    )
}