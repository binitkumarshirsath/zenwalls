package com.binit.zenwalls.ui.screens.favourite

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import com.binit.zenwalls.domain.model.UnsplashImage
import com.binit.zenwalls.ui.components.BackButton
import com.binit.zenwalls.ui.components.ImagePreview
import com.binit.zenwalls.ui.navigation.Routes
import com.binit.zenwalls.ui.screens.wallpaper_list.components.ImageContainer
import com.binit.zenwalls.ui.theme.Primary
import com.binit.zenwalls.ui.theme.Secondary
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
                colors = TopAppBarColors(
                    containerColor = Primary,
                    scrolledContainerColor = Primary,
                    navigationIconContentColor = Color.White,
                    titleContentColor = Color.White,
                    actionIconContentColor = Color.White
                ),
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
                actions = {
                    Icon(
                        Icons.Default.Search, contentDescription = "search_icon",
                        modifier
                            .padding(end = 10.dp)
                            .clickable { navcontroller.navigate(Routes.SearchScreen) }
                    )
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

            if (favouriteImages.itemCount == 0) {
                Column(
                    modifier
                        .fillMaxSize()
                        .background(Primary.copy(alpha = 0.9f)),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(Modifier.height(200.dp))
                    Icon(
                        Icons.Default.Favorite,
                        contentDescription = "favourite_icon",
                        modifier.size(80.dp),
                        tint = Secondary.copy(
                            alpha = 0.8f
                        )
                    )
                    Spacer(Modifier.height(30.dp))
                    Text(
                        text = "No favourites yet",
                        fontSize = 30.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                    Spacer(Modifier.height(10.dp))
                    Text(
                        text = "Add some favourites to see them here",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Light,
                        color = Color.White
                    )

                }
            }

            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Adaptive(150.dp),
                userScrollEnabled = true,
                modifier = modifier
                    .fillMaxSize().background(Primary.copy(alpha = 0.9f))
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