package com.binit.zenwalls.ui.screens.search

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.binit.zenwalls.domain.model.UnsplashImage
import com.binit.zenwalls.ui.components.ImagePreview
import com.binit.zenwalls.ui.screens.search.components.SearchBar
import com.binit.zenwalls.ui.screens.search.components.ShowHints
import com.binit.zenwalls.ui.screens.wallpaper_list.components.ImageContainer
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

private const val TAG = "SearchScreen"

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SearchScreen(
    navcontroller: NavHostController,
    snackbarHostState: SnackbarHostState,
    searchScreenViewModel: SearchScreenViewModel = koinViewModel(),
    onImageClick: (imageId: String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val focusRequester = remember { FocusRequester() }
    val query by searchScreenViewModel.query.collectAsState()
    var showHints by remember { mutableStateOf(true) }
    val images = searchScreenViewModel.images.collectAsLazyPagingItems()



    val isPreviewVisible = remember { mutableStateOf(false) }
    val previewImage = remember { mutableStateOf<UnsplashImage?>(null) }


    LaunchedEffect(key1 = Unit) {
        focusRequester.requestFocus()
    }

    Log.d(TAG, "images: ${images.itemCount}")

    Box(
        modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Column(
            Modifier.fillMaxSize()
        ) {
            if (isPreviewVisible.value && previewImage.value != null) {
                ImagePreview(image = previewImage.value!!)
            }
            SearchBar(
                focusRequester = focusRequester,
                query = query,
                onQueryChange = {
                    searchScreenViewModel.setSearchQuery(it)
                    showHints = it.isEmpty()
                },
                onSearch ={
                    searchScreenViewModel.searchImages()
                    showHints = false
                    focusRequester.freeFocus()
                },
                onQueryClear = {
                    searchScreenViewModel.setSearchQuery("")
                    showHints = true
                },
                navHostController = navcontroller,
                modifier = modifier
            )
            Spacer(Modifier.height(8.dp))

            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Adaptive(150.dp)
            ) {
                items(count = images.itemCount) {
                    images[it]?.let { it1 ->
                        ImageContainer(
                            modifier,
                            onPreviewImageClick = {unsplashimage->
                                Log.d(TAG,"onPreviewImageClick Ran")
                                isPreviewVisible.value = true
                                previewImage.value = unsplashimage
                            },
                            onPreviewImageEnd = {
                                Log.d(TAG,"onPreviewImageClick End")
                                isPreviewVisible.value = false
                                previewImage.value = null
                            },
                            image = it1, onImageClick
                        )
                    }
                }
            }



        }
    }

}
