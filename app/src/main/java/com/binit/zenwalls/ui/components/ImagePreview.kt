package com.binit.zenwalls.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.memory.MemoryCache
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.binit.zenwalls.domain.model.UnsplashImage
import com.binit.zenwalls.ui.theme.Primary


@Composable
fun ImagePreview(
    image: UnsplashImage,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val imageRequest = ImageRequest.Builder(context)
        .data(image.imageUrlRegular)
        .placeholderMemoryCacheKey(MemoryCache.Key(image.imageUrlRegular ?: ""))
        .crossfade(true)
        .build()

    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        // Blurry effect applied only to a separate Box layer
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Color.Unspecified.copy(
                        alpha = 0.6f
                    )
                )
        )

        Column(
            modifier = Modifier.padding(10.dp)
        ) {
            AnimatedVisibility(
                visible = true,
                enter = fadeIn() + scaleIn(),
                exit = fadeOut() + scaleOut(),

                ) {
                Column {


                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
                            .height(50.dp)
                            .background(Primary),

                        ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Primary)
                        ) {
                            PhotographerInfo(image)
                        }
                    }
                    AsyncImage(
                        model = imageRequest,
                        contentDescription = image.description,
                        contentScale = ContentScale.FillBounds,
                    )
                }
            }


        }
    }
}