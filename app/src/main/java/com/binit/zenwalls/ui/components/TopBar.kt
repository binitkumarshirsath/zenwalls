package com.binit.zenwalls.ui.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import com.binit.zenwalls.ui.theme.Primary
import com.binit.zenwalls.ui.theme.Scroll
import com.binit.zenwalls.ui.theme.Tertiary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    scrollBehavior: TopAppBarScrollBehavior,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {
            Text("Zen Walls")
        },
        scrollBehavior = scrollBehavior ,
        colors = TopAppBarColors(
            containerColor = Primary,
            scrolledContainerColor = Scroll,
            navigationIconContentColor = Tertiary,
            titleContentColor = Tertiary,
            actionIconContentColor = Tertiary
        )
    )
}