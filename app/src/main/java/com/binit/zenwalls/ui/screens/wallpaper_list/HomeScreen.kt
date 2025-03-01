package com.binit.zenwalls.ui.screens.wallpaper_list

import android.util.Log
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import org.koin.androidx.compose.koinViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.unit.dp
import com.binit.zenwalls.ui.screens.wallpaper_list.components.ImageContainer

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier, viewModel: HomeScreenViewModel = koinViewModel()
) {
    val images by viewModel.images.collectAsState()
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Adaptive(150.dp)
    ) {
        items(images){
            ImageContainer(modifier,it)
        }
    }

}