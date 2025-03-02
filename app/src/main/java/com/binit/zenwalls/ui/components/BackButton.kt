package com.binit.zenwalls.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun BackButton(onBackClick: () -> Unit,modifier: Modifier = Modifier) {
    Box {
        Icon(
            Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "back_btn",
            modifier.size(20.dp).clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                onBackClick.invoke()
            }
        )
    }
}