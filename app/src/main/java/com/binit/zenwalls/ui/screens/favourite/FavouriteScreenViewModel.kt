package com.binit.zenwalls.ui.screens.favourite

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.binit.zenwalls.domain.model.UnsplashImage
import com.binit.zenwalls.domain.repository.WallpaperRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

private const val TAG = "FavouriteScreenViewModel"
class FavouriteScreenViewModel(
    private val wallpaperRepository: WallpaperRepository,
    ) : ViewModel() {

    val favouriteImages = wallpaperRepository.getAllFavouritesImages()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = PagingData.empty()
        ) .cachedIn(viewModelScope)

    val favouriteImageIds: StateFlow<List<String>> = wallpaperRepository.getFavouritesImageIds()
        .catch { exception->
            Log.e(TAG, "Error fetching favourite images: ${exception.message}")
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )


    fun toggleFavouriteImage(image: UnsplashImage) {
        CoroutineScope(Dispatchers.IO).launch {
            wallpaperRepository.toggleFavouriteStatus(image)
        }
    }
}