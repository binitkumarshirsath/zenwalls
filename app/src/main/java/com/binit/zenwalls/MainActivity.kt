package com.binit.zenwalls

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.binit.zenwalls.ui.components.TopBar
import com.binit.zenwalls.ui.navigation.NavGraph
import com.binit.zenwalls.ui.theme.ZenWallsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ZenWallsTheme {
                val navHostController = rememberNavController()

                Scaffold(
                    topBar = {
                        TopBar()
                    }
                ) {
                    NavGraph(navHostController, modifier = Modifier.padding(it))
                }
            }
        }
    }
}

