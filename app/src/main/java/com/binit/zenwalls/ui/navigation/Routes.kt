package com.binit.zenwalls.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Routes {
    @Serializable
    data object HomeScreen : Routes()
    @Serializable
    data class WallpaperScreen(val wallpaperId: String) : Routes()
    @Serializable
    data object SearchScreen : Routes()
    @Serializable
    data object FavouriteScreen : Routes()
    @Serializable
    data class ProfileScreen(val profileId: String) : Routes()
}