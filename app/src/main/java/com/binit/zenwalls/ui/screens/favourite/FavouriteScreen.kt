package com.binit.zenwalls.ui.screens.favourite

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import com.binit.zenwalls.domain.model.UnsplashImage
import com.binit.zenwalls.ui.components.BackButton
import com.binit.zenwalls.ui.components.ImagePreview
import com.binit.zenwalls.ui.screens.wallpaper_list.components.ImageContainer
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavouriteScreen(
    navcontroller: NavHostController,
    snackbarHostState: SnackbarHostState,
    scrollBehavior: TopAppBarScrollBehavior,
    favouriteScreenViewModel: FavouriteScreenViewModel = koinViewModel(),
    onImageClick: (imageId: String) -> Unit = {},
    modifier: Modifier = Modifier
) {

    val favouriteImages = favouriteScreenViewModel.favouriteImages.collectAsLazyPagingItems()
    val favouritedImageIds by favouriteScreenViewModel.favouriteImageIds.collectAsStateWithLifecycle()
    val isPreviewVisible = remember { mutableStateOf(false) }
    val previewImage = remember { mutableStateOf<UnsplashImage?>(null) }

    Box(
        modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        Column(
            Modifier.fillMaxSize()
        ) {
            TopAppBar(
                scrollBehavior = scrollBehavior,
                title = {
                    Row {
                        Spacer(Modifier.width(20.dp))
                        Text(
                            text = "Walls"
                        )
                        Text("Hearts")
                    }
                },
                navigationIcon = {
                    Row {
                        Spacer(Modifier.width(10.dp))
                        BackButton(
                            onBackClick = {
                                navcontroller.navigateUp()
                            }
                        )
                    }
                },
            )

            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Adaptive(150.dp),
                userScrollEnabled = true,
                modifier = modifier
                    .fillMaxSize()
            ) {
                items(count = favouriteImages.itemCount) {
                    favouriteImages[it]?.let { it1 ->
                        ImageContainer(
                            modifier,
                            onToggleFavouriteStatus = { unsplashImage ->
                                favouriteScreenViewModel.toggleFavouriteImage(unsplashImage)
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
                            image = it1,
                            onImageClick
                        )
                    }
                }
            }




        }

        if (isPreviewVisible.value && previewImage.value != null) {
            ImagePreview(image = previewImage.value!!)
        }
    }
}