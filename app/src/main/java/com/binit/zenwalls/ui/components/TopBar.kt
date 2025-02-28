package com.binit.zenwalls.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.binit.zenwalls.ui.theme.Primary
import com.binit.zenwalls.ui.theme.Scroll
import com.binit.zenwalls.ui.theme.Secondary
import com.binit.zenwalls.ui.theme.Tertiary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(modifier: Modifier = Modifier) {
    TopAppBar(
        title = {
            Text("Zen Walls")
        },
       colors = TopAppBarColors(
           containerColor = Primary,
           scrolledContainerColor = Scroll,
           navigationIconContentColor = Tertiary,
           titleContentColor = Tertiary,
           actionIconContentColor = Tertiary
       )
    )
}