package com.binit.zenwalls.ui.navigation

sealed class Routes {
    data object HomeScreen : Routes()
    data class WallpaperScreen(val wallpaperId: String) : Routes()
    data object SearchScreen : Routes()
    data object FavouriteScreen : Routes()
    data class ProfileScreen(val profileId: String) : Routes()
}