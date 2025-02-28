package com.binit.zenwalls.ui.navigation

import androidx.compose.runtime.Composable

import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.binit.zenwalls.ui.screens.wallpaper_list.HomeScreen

@Composable
fun NavGraph(
    navController: NavHostController
){
    NavHost( navController = navController ,startDestination = Routes.HomeScreen) {
        composable<Routes.HomeScreen> {
            HomeScreen()
        }
    }
}