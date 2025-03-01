package com.binit.zenwalls.ui.screens.wallpaper_list

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.binit.zenwalls.ui.components.TopBar
import com.binit.zenwalls.ui.navigation.Routes
import com.binit.zenwalls.ui.screens.wallpaper_list.components.ImageContainer
import org.koin.androidx.compose.koinViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier, viewModel: HomeScreenViewModel = koinViewModel(),
    onImageClick: (imageId: String) -> Unit = {},
    navController: NavHostController,
    scrollBehavior: TopAppBarScrollBehavior,

    ) {

    Scaffold(
        topBar = {
            TopBar(
                scrollBehavior,
                onSearchClick = {
                    navController.navigate(Routes.SearchScreen)
                }
            )
        },
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
    ) { paddingValues ->
        val images by viewModel.images.collectAsState()

        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Adaptive(150.dp),
            userScrollEnabled = true,
            modifier = Modifier
                .fillMaxSize()
                .nestedScroll(scrollBehavior.nestedScrollConnection)
                .padding(paddingValues)
        ) {
            items(images) {
                ImageContainer(modifier, it, onImageClick)
            }
        }
    }


}