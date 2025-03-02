package com.binit.zenwalls.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.binit.zenwalls.domain.model.UnsplashImage

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
        text = image.photographerName?.uppercase() ?: "Anonymous",
        color = Color.White
    )
}