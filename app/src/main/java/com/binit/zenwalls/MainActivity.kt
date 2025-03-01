package com.binit.zenwalls

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.ui.platform.LocalContext
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.binit.zenwalls.ui.navigation.NavGraph
import com.binit.zenwalls.ui.theme.ZenWallsTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            ZenWallsTheme {
                val context = LocalContext.current
                val currentTheme = context.theme.obtainStyledAttributes(intArrayOf(android.R.attr.windowBackground))
                Log.d("ThemeChecker", "Current theme is applied")
                currentTheme.recycle()
                val navHostController = rememberNavController()
                val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(
                    state = rememberTopAppBarState(),
                    canScroll = { true },
                    snapAnimationSpec = tween(
                        durationMillis = 2000,
                        delayMillis = 1000,
                        easing = FastOutSlowInEasing
                    )
                )
                NavGraph(
                    navHostController,
                    scrollBehavior,
                )
            }
        }
    }
}

