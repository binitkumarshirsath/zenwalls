package com.binit.zenwalls.ui.screens.wallpaper_list.components


import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
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
import com.binit.zenwalls.ui.theme.Primary
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


private const val TAG = "ImageContainer"

@Composable
fun ImageContainer(
    modifier: Modifier = Modifier,
    onToggleFavouriteStatus: (image: UnsplashImage) -> Unit = {},
    favouritedImageIds: List<String> = emptyList(),
    onPreviewImageClick: (image: UnsplashImage) -> Unit,
    onPreviewImageEnd: () -> Unit,
    image: UnsplashImage,
    onImageClick: (imageId: String) -> Unit = {},
) {
    var isLoading by remember { mutableStateOf(true) }
    var isAnimating by remember { mutableStateOf(false) }
    val isFavourite = favouritedImageIds.contains(image.id)
    val coroutineScope = rememberCoroutineScope()

    val aspectRatio by remember {
        derivedStateOf { (image.width?.toFloat() ?: 1f) / (image.height?.toFloat() ?: 1f) }
    }

    val imageRequest = ImageRequest
        .Builder(LocalContext.current)
        .data(image.imageUrlSmall)
        .crossfade(true)
        .build()


    val colorStops = arrayOf(
        0.1f to Primary.copy(alpha = 0.2f),
        0.4f to Primary.copy(alpha = 0.6f),
        0.1f to Primary.copy(alpha = 0.8f)
    )

    val scale by animateFloatAsState(
        targetValue = if (isAnimating) 1.5f else 1f,
        animationSpec = tween(durationMillis = 200),
        label = "Scale Animation"
    )

    Column {
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
            Row(
                modifier
                    .fillMaxWidth()
                    .background(Brush.verticalGradient(colorStops = colorStops)),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Box(
                    modifier
                        .size(30.dp)
                        .padding(end = 8.dp, bottom = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    IconButton(
                        onClick = {
                            onToggleFavouriteStatus.invoke(image)
                            isAnimating = true
                            coroutineScope.launch {
                                delay(400) // Delay for effect
                                isAnimating = false
                            }
                        },
                        modifier = Modifier
                            .fillMaxSize()
                            .align(Alignment.BottomEnd)
                            .scale(scale)
                    ) {
                        Icon(
                            imageVector = if (isFavourite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = "Favourite",
                            modifier = Modifier.size(20.dp),
                            tint = if (isFavourite) Color.Red else Color.White,
                        )
                    }
                }

            }
        }
    }
}