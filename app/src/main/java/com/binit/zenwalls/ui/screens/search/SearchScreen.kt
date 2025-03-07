package com.binit.zenwalls.ui.screens.search

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import com.binit.zenwalls.domain.model.UnsplashImage
import com.binit.zenwalls.ui.components.ImagePreview
import com.binit.zenwalls.ui.screens.search.components.SearchBar
import com.binit.zenwalls.ui.screens.search.components.ShowHints
import com.binit.zenwalls.ui.screens.wallpaper_list.components.ImageContainer
import kotlinx.coroutines.delay
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
    val focusManager = LocalFocusManager.current

    val query by searchScreenViewModel.query.collectAsState()
    var showHints by remember { mutableStateOf(true) }
    val images = searchScreenViewModel.images.collectAsLazyPagingItems()
    val isPreviewVisible = remember { mutableStateOf(false) }
    val previewImage = remember { mutableStateOf<UnsplashImage?>(null) }

    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(key1 = Unit) {
        delay(200)
        Log.d(TAG, "Requesting focus")
        focusRequester.requestFocus()
        Log.d(TAG, "Focus requested")
    }

    Log.d(TAG, "focus: $focusRequester")

    Box(
        modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        Column(
            Modifier.fillMaxSize()
        ) {
            val keyboardController = LocalSoftwareKeyboardController.current
            SearchBar(
                focusRequester = focusRequester,
                keyboardController = keyboardController,
                query = query,
                onQueryChange = {
                    searchScreenViewModel.setSearchQuery(it)
                    showHints = it.isEmpty()
                },
                onSearch = {
                    coroutineScope .launch{
                        keyboardController?.hide()
                        delay(100) // Give keyboard time to start hiding
                        searchScreenViewModel.searchImages()
                        showHints = false
                    }

                },
                onQueryClear = {
                    searchScreenViewModel.setSearchQuery("")
                    showHints = true
                },
                navHostController = navcontroller,
                modifier = modifier
            )


            if (showHints) {
                ShowHints(
                    setQuery = {
                        searchScreenViewModel.setSearchQuery(it)
                        searchScreenViewModel.searchImages()
                        showHints = false
                        focusRequester.freeFocus()
                        keyboardController?.hide()
                    },

                )
                Spacer(Modifier.height(8.dp))
            }

            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Adaptive(150.dp),
                userScrollEnabled = true
            ) {
                items(count = images.itemCount) {
                    images[it]?.let { it1 ->
                        ImageContainer(
                            modifier,
                            onPreviewImageClick = { unsplashimage ->
                                Log.d(TAG, "onPreviewImageClick Ran")
                                isPreviewVisible.value = true
                                previewImage.value = unsplashimage
                            },
                            onPreviewImageEnd = {
                                Log.d(TAG, "onPreviewImageClick End")
                                isPreviewVisible.value = false
                                previewImage.value = null
                            },
                            image = it1, onImageClick
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
