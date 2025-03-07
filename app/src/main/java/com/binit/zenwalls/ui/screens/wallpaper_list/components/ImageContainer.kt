package com.binit.zenwalls.ui.screens.wallpaper_list.components

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.binit.zenwalls.domain.model.UnsplashImage
import com.binit.zenwalls.ui.components.ShimmerEffect


private const val TAG = "ImageContainer"

@Composable
fun ImageContainer(
    modifier: Modifier = Modifier,
    onPreviewImageClick: (image: UnsplashImage) -> Unit,
    onPreviewImageEnd: () -> Unit,
    image: UnsplashImage,
    onImageClick: (imageId: String) -> Unit = {},
) {

    var isLoading by remember { mutableStateOf(true) }
    val aspectRatio by remember {
        derivedStateOf { (image.width?.toFloat() ?: 1f) / (image.height?.toFloat() ?: 1f) }
    }

    val imageRequest =
        ImageRequest
            .Builder(LocalContext.current)
            .data(image.imageUrlSmall)
            .crossfade(true)
            .build()
    if (isLoading) {
        ShimmerEffect(
            modifier
                .aspectRatio(aspectRatio)
                .width(image.width?.dp ?: 0.dp)
                .height(image.height?.dp ?: 0.dp)

        )
    }

        AsyncImage(
            model = imageRequest,
            onState = {
                isLoading = false
            },
            contentDescription = image.description,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .aspectRatio(aspectRatio)
                .clickable {
                    onImageClick.invoke(image.id)
                }
                .pointerInput(Unit) {
                    detectDragGesturesAfterLongPress(
                        onDragStart = {
                            onPreviewImageClick.invoke(image)
                        },
                        onDragCancel = {
                            Log.d(TAG, "onDragCancel ran")
                            onPreviewImageEnd.invoke()
                        },
                        onDragEnd = {
                            Log.d(TAG, "onDragEnd ran")
                            onPreviewImageEnd.invoke()
                        },
                        onDrag = { _, _ -> }
                    )
                }
        )

}