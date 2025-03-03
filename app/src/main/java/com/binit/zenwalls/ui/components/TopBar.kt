package com.binit.zenwalls.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.binit.zenwalls.ui.theme.Primary
import com.binit.zenwalls.ui.theme.Scroll
import com.binit.zenwalls.ui.theme.Tertiary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    scrollBehavior: TopAppBarScrollBehavior,
    onSearchClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {
            Text("Zen Walls")
        },
        actions = {
            Icon(
                Icons.Default.Search, contentDescription = "search_icon",
                modifier.padding(end = 10.dp).clickable { onSearchClick.invoke() }
            )
        },
        scrollBehavior = scrollBehavior,
        colors = TopAppBarColors(
            containerColor = Primary,
            scrolledContainerColor = Scroll,
            navigationIconContentColor = Tertiary,
            titleContentColor = Tertiary,
            actionIconContentColor = Tertiary
        ),
    )
}