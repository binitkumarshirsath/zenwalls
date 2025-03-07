package com.binit.zenwalls.ui.navigation

import android.util.Log
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.binit.zenwalls.ui.screens.favourite.FavouriteScreen
import com.binit.zenwalls.ui.screens.favourite.FavouriteScreenViewModel
import com.binit.zenwalls.ui.screens.profile.ProfileScreen
import com.binit.zenwalls.ui.screens.search.SearchScreen
import com.binit.zenwalls.ui.screens.search.SearchScreenViewModel
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
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier
) {

    NavHost(navController = navController, startDestination = Routes.HomeScreen) {
        composable<Routes.HomeScreen> {
            HomeScreen(
                modifier = Modifier,
                snackbarHostState,
                scrollBehavior = scrollBehavior,
                navHostController = navController,
                onImageClick = {
                    navController.navigate(Routes.WallpaperScreen(it))
                }
            )
        }

        composable<Routes.WallpaperScreen> {
            val wallpaperScreenViewModel: WallpaperScreenViewModel = koinViewModel()
            WallpaperScreen(
                modifier = modifier,
                snackbarHostState,
                onBackClick = {
                    navController.navigateUp()
                },
                onProfileClick = {
                    navController.navigate(Routes.ProfileScreen(it))
                },
                wallpaperScreenViewModel = wallpaperScreenViewModel
            )
        }

        composable<Routes.ProfileScreen> {
            val photographerProfileLink = it.toRoute<Routes.ProfileScreen>().photoGrapherProfileLink
            Log.d(TAG, "photographerProfileLink: $photographerProfileLink")
            ProfileScreen(photographerProfileLink, onBackClick = {
                navController.navigateUp()
            }, modifier)
        }

        composable<Routes.SearchScreen> {
            val searchScreenViewModel: SearchScreenViewModel = koinViewModel()

            SearchScreen(
                navController,
                snackbarHostState,
                searchScreenViewModel,
                onImageClick = {
                    navController.navigate(Routes.WallpaperScreen(it))
                }
            )
        }

        composable<Routes.FavouriteScreen> {
            val favouriteScreenViewModel:FavouriteScreenViewModel = koinViewModel()
            FavouriteScreen(
                navcontroller = navController,
                snackbarHostState = snackbarHostState,
                scrollBehavior = scrollBehavior,
                favouriteScreenViewModel = favouriteScreenViewModel,
                onImageClick = {  },
                modifier = Modifier
            )
        }

    }
}