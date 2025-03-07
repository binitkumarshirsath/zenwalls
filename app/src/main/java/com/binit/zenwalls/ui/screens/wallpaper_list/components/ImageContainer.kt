package com.binit.zenwalls.ui.screens.wallpaper_list.components


import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePainter
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.binit.zenwalls.domain.model.UnsplashImage
import com.binit.zenwalls.ui.components.ShimmerEffect
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


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
    val coroutineScope = rememberCoroutineScope()
    val aspectRatio by remember {
        derivedStateOf { (image.width?.toFloat() ?: 1f) / (image.height?.toFloat() ?: 1f) }
    }

    val imageRequest = ImageRequest
        .Builder(LocalContext.current)
        .data(image.imageUrlSmall)
        .crossfade(true)
        .build()

    Box(
        modifier = modifier
            .aspectRatio(aspectRatio)
            .clickable { onImageClick(image.id) }
            .pointerInput(Unit) {
                detectDragGesturesAfterLongPress(
                    onDragStart = { onPreviewImageClick(image) },
                    onDragCancel = { onPreviewImageEnd() },
                    onDragEnd = { onPreviewImageEnd() },
                    onDrag = { _, _ -> }
                )
            }
    ) {
        if (isLoading) {
            ShimmerEffect(
                modifier = Modifier.fillMaxSize()
            )
        }

        AsyncImage(
            model = imageRequest,
            onState = { state ->
               coroutineScope.launch {
                   delay(400)
                   isLoading = if(state is AsyncImagePainter.State.Success){
                       false
                   }else{
                       true
                   }
               }
            },
            contentDescription = image.description,
            contentScale = ContentScale.Fit,
            modifier = Modifier.fillMaxSize()
        )
    }
}