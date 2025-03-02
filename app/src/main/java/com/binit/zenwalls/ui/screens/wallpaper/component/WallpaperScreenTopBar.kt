package com.binit.zenwalls.ui.screens.wallpaper.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.binit.zenwalls.R
import com.binit.zenwalls.domain.model.UnsplashImage
import com.binit.zenwalls.ui.components.BackButton
import com.binit.zenwalls.ui.components.PhotographerInfo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WallpaperScreenTopBar(
    image: UnsplashImage?,
    onBackClick: () -> Unit,
    showBottomSheet: () -> Unit,
    onProfileClick: (profileUrl: String) -> Unit,
    modifier: Modifier = Modifier
) {

    TopAppBar(
        navigationIcon = {
            Row {
                Spacer(Modifier.width(10.dp))
                BackButton(onBackClick)
            }

        },
        title = {
            if (image != null) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        image.photographerProfileLink?.let { onProfileClick.invoke(it) }
                    }
                ) {
                    PhotographerInfo(image)
                }
            }
        },
        actions = {
            Row {
                Icon(
                    painter = painterResource(R.drawable.baseline_arrow_downward_24),
                    contentDescription = "download_btn",
                    modifier
                        .size(20.dp)
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ) {
                            showBottomSheet.invoke()
                        }
                )
                Spacer(Modifier.width(10.dp))
            }
        }
    )

//    Row(
//        modifier = Modifier
//            .padding(top = 20.dp)
//            .fillMaxWidth()
//            .height(60.dp)
//            .background(Secondary),
//        verticalAlignment = Alignment.CenterVertically,
//    ) {
//        Spacer(modifier.padding(start = 10.dp))
//
//        if (image != null) {
//            PhotographerInfo(image)
//        }
//        Spacer(Modifier.weight(1f))
//        //Download btn
//        Icon(
//            painter = painterResource(R.drawable.baseline_arrow_downward_24),
//            contentDescription = "download_btn",
//            modifier.size(20.dp)
//        )
//        Spacer(modifier.padding(end = 10.dp))
//    }
}