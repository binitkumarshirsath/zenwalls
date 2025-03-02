package com.binit.zenwalls.ui.screens.wallpaper


import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.binit.zenwalls.ui.screens.wallpaper.component.WallpaperBottomSheet
import com.binit.zenwalls.ui.screens.wallpaper.component.WallpaperContainer
import com.binit.zenwalls.ui.screens.wallpaper.component.WallpaperScreenTopBar
import org.koin.androidx.compose.koinViewModel

private const val TAG = "WallpaperScreen"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WallpaperScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    onProfileClick: (profileUrl: String) -> Unit = {},
    wallpaperScreenViewModel: WallpaperScreenViewModel = koinViewModel()
) {
    val image = wallpaperScreenViewModel.image.collectAsState()

    val sheetState = rememberModalBottomSheetState()

    var isBottomSheetVisible by remember { mutableStateOf(false) }

    Column {
        WallpaperScreenTopBar(
            image = image.value,
            onBackClick = onBackClick,
            showBottomSheet = {
                isBottomSheetVisible = true
            },
            onProfileClick = onProfileClick
        )
        image.value?.let { WallpaperContainer(image = it) }

        if (isBottomSheetVisible) {
            WallpaperBottomSheet(
                wallpaperScreenViewModel = wallpaperScreenViewModel,
                sheetState = sheetState,
                onDismiss = {
                    isBottomSheetVisible = false
                }
            )
        }
    }
}


enum class IMAGE_QUALITY {
    SMALL,
    REGULAR,
    HIGH,
    RAW
}
