package com.binit.zenwalls.ui.screens.wallpaper_list.components

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil3.compose.AsyncImage
import com.binit.zenwalls.domain.model.UnsplashImage

@Composable
fun ImageContainer(modifier: Modifier = Modifier,image:UnsplashImage) {
    val aspectRatio by remember {
        derivedStateOf { (image.width?.toFloat() ?: 1f) / (image.height?.toFloat()?:1f)}
    }
    AsyncImage(
        model = image.imageUrlRegular,
        contentDescription = image.description,
        contentScale = ContentScale.Fit,
        modifier = Modifier.aspectRatio(aspectRatio)
    )
}