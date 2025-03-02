package com.binit.zenwalls

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.navigation.compose.rememberNavController
import com.binit.zenwalls.domain.repository.NetworkConnectivityObserver
import com.binit.zenwalls.ui.components.NetworkStatusBar
import com.binit.zenwalls.ui.components.TopBar
import com.binit.zenwalls.ui.navigation.NavGraph
import com.binit.zenwalls.ui.navigation.Routes
import com.binit.zenwalls.ui.theme.ZenWallsTheme
import org.koin.android.ext.android.inject

private const val TAG = "MainActivity"

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
                    canScroll = { true },
                )

                val networkConnectivityObserver: NetworkConnectivityObserver by inject()
                val networkStatus by networkConnectivityObserver.networkStatus.collectAsState()



                Scaffold(
                    topBar = {
                        TopBar(
                            scrollBehavior,
                            onSearchClick = {
                                navHostController.navigate(Routes.SearchScreen)
                            }
                        )
                    },
                    bottomBar = {
                        NetworkStatusBar(
                            modifier = Modifier.navigationBarsPadding(),
                            networkStatus = networkStatus
                        )
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .nestedScroll(scrollBehavior.nestedScrollConnection),
                ) { paddingValues ->
                    NavGraph(
                        navHostController,
                        modifier = Modifier.padding(paddingValues),
                    )
                }
            }
        }
    }
}

