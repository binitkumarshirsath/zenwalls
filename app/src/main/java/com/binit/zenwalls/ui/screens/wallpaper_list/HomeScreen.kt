package com.binit.zenwalls.ui.screens.wallpaper_list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.binit.zenwalls.domain.model.UnsplashImage
import com.binit.zenwalls.ui.components.ImagePreview
import com.binit.zenwalls.ui.components.TopBar
import com.binit.zenwalls.ui.navigation.Routes
import com.binit.zenwalls.ui.screens.wallpaper_list.components.ImageContainer
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier,
    snackBarHostState: SnackbarHostState,
    scrollBehavior: TopAppBarScrollBehavior,
    navHostController: NavHostController,
    viewModel: HomeScreenViewModel = koinViewModel(),
    onImageClick: (imageId: String) -> Unit = {},
) {

    val isPreviewVisible = remember { mutableStateOf(false) }
    val previewImage = remember { mutableStateOf<UnsplashImage?>(null) }
    val images by viewModel.images.collectAsState()
    val favouritedImageIds by  viewModel.favouriteImageIds.collectAsStateWithLifecycle()

    val scope = rememberCoroutineScope()
    LaunchedEffect(viewModel.snackBarEvent) {
        viewModel.snackBarEvent.collect { event ->
            scope.launch {
                snackBarHostState.showSnackbar(
                    message = event.message,
                    duration = event.duration
                )
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {

        Column(modifier = Modifier.fillMaxSize()) {
            TopBar(
                scrollBehavior,
                onSearchClick = {
                    navHostController.navigate(Routes.SearchScreen)
                }
            )

            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Adaptive(150.dp),
                userScrollEnabled = true,
                modifier = modifier
                    .fillMaxSize()
            ) {
                items(images) {
                     ImageContainer(
                        modifier,
                        onToggleFavouriteStatus ={unsplashImage->
                            viewModel.toggleFavouriteImage(unsplashImage)
                        },
                        favouritedImageIds,
                        onPreviewImageClick = {
                            isPreviewVisible.value = true
                            previewImage.value = it
                        },
                        onPreviewImageEnd = {
                            isPreviewVisible.value = false
                            previewImage.value = null
                        },
                        it,
                        onImageClick
                    )
                }
            }


        }
        FloatingActionButton(
            onClick = {
                navHostController.navigate(Routes.FavouriteScreen)
            },
            modifier = Modifier.align(Alignment.BottomEnd).padding(end = 10.dp, bottom = 10.dp),
            containerColor = Color.Transparent.copy(
                alpha = 0.2f
            ),
            shape = FloatingActionButtonDefaults.largeShape
        ) {
            Icon(
                Icons.Default.Favorite,
                contentDescription = "favourite_icon",
                tint = Color.Red,
                modifier = Modifier.size(40.dp)
            )
        }
        if (isPreviewVisible.value && previewImage.value != null) {
            ImagePreview(image = previewImage.value!!)
        }
    }
}

