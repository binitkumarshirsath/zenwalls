package com.binit.zenwalls.ui.screens.wallpaper


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.binit.zenwalls.ui.screens.wallpaper.component.WallpaperScreenTopBar
import com.binit.zenwalls.ui.theme.BackGround
import com.binit.zenwalls.ui.theme.Scroll
import org.koin.androidx.compose.koinViewModel

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
            modifier.fillMaxSize().background(BackGround),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            AsyncImage(
                model = imageRequest,
                contentDescription = image.value?.description,
                modifier = modifier
            )
        }
    }
}