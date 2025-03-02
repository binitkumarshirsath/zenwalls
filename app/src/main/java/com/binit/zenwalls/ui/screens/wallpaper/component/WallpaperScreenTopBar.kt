package com.binit.zenwalls.ui.screens.wallpaper.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.binit.zenwalls.R
import com.binit.zenwalls.domain.model.UnsplashImage
import com.binit.zenwalls.ui.components.BackButton
import com.binit.zenwalls.ui.components.PhotographerInfo
import com.binit.zenwalls.ui.theme.Secondary

@Composable
fun WallpaperScreenTopBar(
    image: UnsplashImage?,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = Modifier
            .padding(top = 20.dp)
            .fillMaxWidth()
            .height(60.dp)
            .background(Secondary),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Spacer(modifier.padding(start = 10.dp))
        BackButton(onBackClick)
        if (image != null) {
            PhotographerInfo(image)
        }
        Spacer(Modifier.weight(1f))
        //Download btn
        Icon(
            painter = painterResource(R.drawable.baseline_arrow_downward_24),
            contentDescription = "download_btn",
            modifier.size(20.dp)
        )
        Spacer(modifier.padding(end = 10.dp))
    }
}