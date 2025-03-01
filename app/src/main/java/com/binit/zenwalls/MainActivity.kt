package com.binit.zenwalls

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.navigation.compose.rememberNavController
import com.binit.zenwalls.ui.components.TopBar
import com.binit.zenwalls.ui.navigation.NavGraph
import com.binit.zenwalls.ui.theme.ZenWallsTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ZenWallsTheme {
                val navHostController = rememberNavController()
                val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(
                    state = rememberTopAppBarState(),
                    canScroll = {true},
                    snapAnimationSpec = tween(
                        durationMillis = 2000,
                        delayMillis = 1000,
                        easing = FastOutSlowInEasing
                    )
                )
                Scaffold(
                    topBar = {
                        TopBar(
                            scrollBehavior
                        )
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .nestedScroll(scrollBehavior.nestedScrollConnection),
                ) { paddingValues ->
                    NavGraph(
                        navHostController,
                        scrollBehavior,
                        modifier = Modifier
                            .padding(paddingValues)

                    )
                }
            }
        }
    }
}

