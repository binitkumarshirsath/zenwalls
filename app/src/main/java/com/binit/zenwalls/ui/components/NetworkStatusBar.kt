package com.binit.zenwalls.ui.components


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.binit.zenwalls.domain.model.NetworkStatus
import kotlinx.coroutines.delay


@Composable
fun NetworkStatusBar(
    modifier: Modifier = Modifier,
    networkStatus: NetworkStatus = NetworkStatus.DISCONNECTED
) {
    var textToShow by remember { mutableStateOf("") }
    var backgroundColor by remember { mutableStateOf(Color.Red) }
    var isNetworkStatusBarVisible by remember { mutableStateOf(false) }

    when (networkStatus) {
        NetworkStatus.CONNECTED -> {
            textToShow = "Connected to Internet"
            backgroundColor = Color.Green.copy(
                green = 0.7f
            )
        }

        NetworkStatus.DISCONNECTED -> {
            textToShow = "No internet connection"
            backgroundColor = Color.Red
            isNetworkStatusBarVisible = true
        }
    }

    LaunchedEffect(key1 = networkStatus) {
        if (networkStatus == NetworkStatus.CONNECTED) {
            isNetworkStatusBarVisible = true
            delay(2000)
            isNetworkStatusBarVisible = false
        }
    }

    AnimatedVisibility(
        visible = isNetworkStatusBarVisible,
        enter = slideInVertically(
            animationSpec = tween(
                durationMillis = 1600
            )
        ) { h -> h },
        exit = slideOutVertically(
            animationSpec = tween(
                durationMillis = 1600
            )
        ) { h -> h }

    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .background(color = backgroundColor),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = textToShow,
                color = Color.White,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(2.dp)
            )
        }
    }


}