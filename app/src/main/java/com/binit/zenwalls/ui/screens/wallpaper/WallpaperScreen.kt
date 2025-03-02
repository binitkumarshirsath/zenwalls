package com.binit.zenwalls.ui.screens.wallpaper


import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.binit.zenwalls.ui.screens.wallpaper.component.WallpaperScreenTopBar
import com.binit.zenwalls.ui.theme.BackGround
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

private const val TAG = "WallpaperScreen"

@Composable
fun WallpaperScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    onProfileClick:(profileUrl:String)->Unit={},
    wallpaperScreenViewModel: WallpaperScreenViewModel = koinViewModel()
) {
    val image = wallpaperScreenViewModel.image.collectAsState()
    val imageRequest = ImageRequest
        .Builder(LocalContext.current)
        .data(image.value?.imageUrlRaw)
        .build()

    Column {
        WallpaperScreenTopBar(image= image.value,onBackClick= onBackClick, onProfileClick = onProfileClick)

        Column(
            modifier
                .fillMaxSize()
                .background(BackGround),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            BoxWithConstraints(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                // Use animatable for smooth animation
                val animatedScale = remember { Animatable(1f) }

                val animatedOffsetX = remember { Animatable(0f) }
                val animatedOffsetY = remember { Animatable(0f) }

                val isImageZoomed: Boolean by remember { derivedStateOf { animatedScale.value != 1f } }

                // Track if we're currently in a gesture
                var isTransforming by remember { mutableStateOf(false) }

                val coroutineScope = rememberCoroutineScope()

                val transformState = rememberTransformableState { zoomChange, offsetChange, _ ->
                    isTransforming = true

                    coroutineScope.launch {
                        // Update scale with animation
                        val targetScale = (animatedScale.value * zoomChange)
                            .coerceIn(0.2f, 10f) // Optional: add min/max scale limits

                        Log.d(TAG, "targetScale: $targetScale")
                        animatedScale.snapTo(targetScale)

                        // Calculate bounds
                        val maxX =
                            ((constraints.maxWidth * (targetScale - 1)) / 2).coerceAtLeast(0f)
                        val maxY =
                            ((constraints.maxHeight * (targetScale - 1)) / 2).coerceAtLeast(0f)

                        // Update offset
                        val newX = (animatedOffsetX.value + offsetChange.x).coerceIn(-maxX, maxX)
                        val newY = (animatedOffsetY.value + offsetChange.y).coerceIn(-maxY, maxY)

                        animatedOffsetX.snapTo(newX)
                        animatedOffsetY.snapTo(newY)
                    }
                }

                fun resetPositionWithAnimation() {
                    coroutineScope.launch {
                        // Use parallel animations for smoother experience
                        launch {
                            animatedScale.animateTo(
                                targetValue = 1f,
                                animationSpec = tween(
                                    durationMillis = 300,
                                    easing = FastOutSlowInEasing
                                )
                            )
                        }

                        launch {
                            animatedOffsetX.animateTo(
                                targetValue = 0f,
                                animationSpec = tween(
                                    durationMillis = 300,
                                    easing = FastOutSlowInEasing
                                )
                            )
                        }

                        launch {
                            animatedOffsetY.animateTo(
                                targetValue = 0f,
                                animationSpec = tween(
                                    durationMillis = 300,
                                    easing = FastOutSlowInEasing
                                )
                            )
                        }
                    }
                }

                // Detect when transformation ends
                LaunchedEffect(transformState.isTransformInProgress) {
                    if (!transformState.isTransformInProgress && isTransforming) {
                        isTransforming = false

                        // If scale is less than threshold (e.g., 1.1f), animate back to 1f
                        if (animatedScale.value < 1.1f && animatedScale.value != 1f) {
                            launch {
                                animatedScale.animateTo(
                                    targetValue = 1f,
                                    animationSpec = spring(
                                        dampingRatio = Spring.DampingRatioMediumBouncy,
                                        stiffness = Spring.StiffnessLow
                                    )
                                )
                            }

                            launch {
                                animatedOffsetX.animateTo(
                                    targetValue = 0f,
                                    animationSpec = spring(
                                        dampingRatio = Spring.DampingRatioMediumBouncy,
                                        stiffness = Spring.StiffnessLow
                                    )
                                )
                            }

                            launch {
                                animatedOffsetY.animateTo(
                                    targetValue = 0f,
                                    animationSpec = spring(
                                        dampingRatio = Spring.DampingRatioMediumBouncy,
                                        stiffness = Spring.StiffnessLow
                                    )
                                )
                            }
                        }
                    }
                }

                AsyncImage(
                    model = imageRequest,
                    contentDescription = image.value?.description,
                    modifier = modifier
                        .transformable(transformState)
                        .graphicsLayer {
                            scaleX = animatedScale.value
                            scaleY = animatedScale.value
                            translationX = animatedOffsetX.value
                            translationY = animatedOffsetY.value
                        }
                        .pointerInput(Unit) {
                            detectTapGestures(
                                onDoubleTap = { tapOffset ->
                                    if(isImageZoomed) {
                                        resetPositionWithAnimation()
                                    } else {
                                        // Calculate the position to zoom in at tap location
                                        // For better tap position accuracy, we need to calculate
                                        // the offset relative to the center
                                        val centerX = size.width / 2
                                        val centerY = size.height / 2


                                        // The distance from tap to center, adjusted for zoom factor
                                        // Negative because we want to move the image in the opposite direction
                                        val targetOffsetX = -(tapOffset.x - centerX) * (3f - 1f)
                                        val targetOffsetY = -(tapOffset.y - centerY) * (3f - 1f)

                                        // Ensure the offset is within bounds for the new scale
                                        val maxX = ((size.width * (3f - 1f)) / 2)
                                        val maxY = ((size.height * (3f - 1f)) / 2)

                                        val boundedOffsetX = targetOffsetX.coerceIn(-maxX, maxX)
                                        val boundedOffsetY = targetOffsetY.coerceIn(-maxY, maxY)

                                        coroutineScope.launch {
                                            // Launch all animations in parallel for synchronized movement
                                            val animationSpec = tween<Float>(
                                                durationMillis = 500,
                                                easing = FastOutSlowInEasing
                                            )

                                            // Launch all animations in parallel
                                            launch {
                                                animatedScale.animateTo(
                                                    targetValue = 5f,
                                                    animationSpec = animationSpec
                                                )
                                            }

                                            launch {
                                                animatedOffsetX.animateTo(
                                                    targetValue = boundedOffsetX,
                                                    animationSpec = animationSpec
                                                )
                                            }

                                            launch {
                                                animatedOffsetY.animateTo(
                                                    targetValue = boundedOffsetY,
                                                    animationSpec = animationSpec
                                                )
                                            }
                                        }
                                    }
                                }
                            )
                        }
                )
            }
        }
    }
}