package com.binit.zenwalls.ui.screens.wallpaper_list.components


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
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
        contentAlignment = Alignment.BottomEnd,
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
                    delay(698)
                    isLoading = if (state is AsyncImagePainter.State.Success) {
                        false
                    } else {
                        true
                    }
                }
            },
            contentDescription = image.description,
            contentScale = ContentScale.Fit,
            modifier = Modifier.fillMaxSize()
        )
        Box(
            modifier
                .size(25.dp)
                .clip(RoundedCornerShape(50))
                .background(
                    Color.Red.copy(alpha = 0.3f),
                )
            ,
            contentAlignment = Alignment.Center
        ) {
            Icon(
                Icons.Default.Favorite,
                contentDescription = "favourite_img",
                modifier.size(20.dp),
                tint = Color.White
            )
        }
    }
}