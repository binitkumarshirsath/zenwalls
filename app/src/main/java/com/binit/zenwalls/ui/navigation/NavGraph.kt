package com.binit.zenwalls.ui.navigation

import android.util.Log
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.binit.zenwalls.domain.model.UnsplashImage
import com.binit.zenwalls.ui.screens.favourite.FavouriteScreen
import com.binit.zenwalls.ui.screens.search.SearchScreen
import com.binit.zenwalls.ui.screens.wallpaper.WallpaperScreen
import com.binit.zenwalls.ui.screens.wallpaper.WallpaperScreenViewModel
import com.binit.zenwalls.ui.screens.wallpaper_list.HomeScreen
import org.koin.androidx.compose.koinViewModel

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

        composable<Routes.WallpaperScreen> {
            val wallpaperScreenViewModel : WallpaperScreenViewModel = koinViewModel()
            val wallpaperId = wallpaperScreenViewModel.imageId
            Log.d(TAG, "wallpaperId: $wallpaperId")
            WallpaperScreen(
                modifier = modifier,
                onBackClick = {
                    navController.navigateUp()
                },
                wallpaperScreenViewModel = wallpaperScreenViewModel
            )
        }

        composable<Routes.SearchScreen> {
            SearchScreen()
        }

        composable<Routes.FavouriteScreen> {
            FavouriteScreen()
        }

    }
}