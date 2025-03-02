package com.binit.zenwalls.ui.screens.wallpaper.component

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.binit.zenwalls.ui.screens.wallpaper.IMAGE_QUALITY
import com.binit.zenwalls.ui.screens.wallpaper.WallpaperScreenViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WallpaperBottomSheet(
    wallpaperScreenViewModel: WallpaperScreenViewModel,
    sheetState: SheetState,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {

    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    ModalBottomSheet(
        onDismissRequest = {
            onDismiss.invoke()
        },
        sheetState = sheetState
    ) {
        IMAGE_QUALITY.entries.map {
            Text(
                text = "$it",
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) {
                        wallpaperScreenViewModel.downloadImage(it)
                        onDismiss.invoke()
                        scope.launch {
                            Toast
                                .makeText(
                                    context,
                                    "Downloading...", Toast.LENGTH_LONG
                                )
                                .show()
                        }
                    }
            )
            Spacer(modifier.height(10.dp))
        }
    }
}