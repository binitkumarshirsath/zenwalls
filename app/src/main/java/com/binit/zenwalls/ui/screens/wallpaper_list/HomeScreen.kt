package com.binit.zenwalls.ui.screens.wallpaper_list

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.binit.zenwalls.domain.model.UnsplashImage
import com.binit.zenwalls.ui.components.ImagePreview
import com.binit.zenwalls.ui.screens.wallpaper_list.components.ImageContainer
import org.koin.androidx.compose.koinViewModel


@Composable
fun HomeScreen(
    modifier: Modifier,
    viewModel: HomeScreenViewModel = koinViewModel(),
    onImageClick: (imageId: String) -> Unit = {},
) {

    val isPreviewVisible = remember { mutableStateOf(false) }
    val previewImage = remember { mutableStateOf<UnsplashImage?>(null) }
    val images by viewModel.images.collectAsState()


    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Adaptive(150.dp),
        userScrollEnabled = true,
        modifier = modifier
            .fillMaxSize()
    ) {
        items(images) {
            ImageContainer(
                modifier,
                onPreviewImageClick = {
                    isPreviewVisible.value = true
                    previewImage.value = it
                },
                onPreviewImageEnd = {
                    isPreviewVisible.value = false
                    previewImage.value = null
                }, it, onImageClick
            )
        }
    }

    if (isPreviewVisible.value && previewImage.value != null) {
        ImagePreview(image = previewImage.value!!)
    }
}

