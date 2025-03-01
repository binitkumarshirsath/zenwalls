package com.binit.zenwalls.ui.navigation

import android.util.Log
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.binit.zenwalls.ui.screens.favourite.FavouriteScreen
import com.binit.zenwalls.ui.screens.search.SearchScreen
import com.binit.zenwalls.ui.screens.wallpaper.WallpaperScreen
import com.binit.zenwalls.ui.screens.wallpaper_list.HomeScreen

private const val TAG = "NavGraph"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavGraph(
    navController: NavHostController,
    scrollBehavior: TopAppBarScrollBehavior,
    modifier: Modifier = Modifier
) {
    NavHost(navController = navController, startDestination = Routes.HomeScreen) {
        composable<Routes.HomeScreen> {
            HomeScreen(
                modifier = modifier,
                navController = navController,
                scrollBehavior = scrollBehavior,
                onImageClick = {
                    navController.navigate(Routes.WallpaperScreen(it))
                }
            )
        }

        composable<Routes.WallpaperScreen> { backStackEntry ->
            val wallpaperId = backStackEntry.toRoute<Routes.WallpaperScreen>().wallpaperId
            Log.d(TAG, "wallpaperId: $wallpaperId")
            WallpaperScreen(wallpaperId)
        }

        composable<Routes.SearchScreen> {
            SearchScreen()
        }

        composable<Routes.FavouriteScreen>{
            FavouriteScreen()
        }

    }
}