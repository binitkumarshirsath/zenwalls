package com.binit.zenwalls.ui.screens.wallpaper


import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.VectorProperty
import androidx.compose.ui.platform.LocalContext
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.binit.zenwalls.ui.screens.wallpaper.component.WallpaperScreenTopBar
import com.binit.zenwalls.ui.theme.BackGround
import org.koin.androidx.compose.koinViewModel
import kotlin.math.max

private const val TAG = "WallpaperScreen"

@Composable
fun WallpaperScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    wallpaperScreenViewModel: WallpaperScreenViewModel = koinViewModel()
) {
    val image = wallpaperScreenViewModel.image.collectAsState()
    val imageRequest = ImageRequest
        .Builder(LocalContext.current)
        .data(image.value?.imageUrlRaw)
        .build()


    Column {
        WallpaperScreenTopBar(image.value, onBackClick)

        Column(
            modifier
                .fillMaxSize()
                .background(BackGround),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            BoxWithConstraints(modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                var scale by remember { mutableFloatStateOf(1f) }
                var offset by remember { mutableStateOf(Offset.Zero) }
                val isImageZoomed: Boolean by remember { derivedStateOf { scale != 1f } }
                val transformState = rememberTransformableState { zoomChange, offsetChange, _ ->
                    scale *= zoomChange
                    val maxX = ((constraints.maxWidth * (scale - 1)) / 2).coerceAtLeast(0f)
                    val maxY = ((constraints.maxHeight * (scale - 1)) / 2).coerceAtLeast(0f)
                    offset = Offset(
                        x = (offset.x + offsetChange.x).coerceIn(-maxX, maxX),
                        y = (offset.y + offsetChange.y).coerceIn(-maxY, maxY)
                    )
                }

                AsyncImage(
                    model = imageRequest,
                    contentDescription = image.value?.description,
                    modifier = modifier.transformable(transformState).graphicsLayer {
                        scaleX = scale
                        scaleY = scale
                        translationX = offset.x
                        translationY = offset.y
                    }
                )
            }

        }
    }
}