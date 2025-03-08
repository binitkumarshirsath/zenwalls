package com.binit.zenwalls.ui.screens.wallpaper_list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.binit.zenwalls.domain.NetworkErrorToMessageMapper
import com.binit.zenwalls.domain.model.UnsplashImage
import com.binit.zenwalls.domain.networkUtil.onError
import com.binit.zenwalls.domain.networkUtil.onSuccess
import com.binit.zenwalls.domain.repository.WallpaperRepository
import com.binit.zenwalls.domain.util.SnackBarEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

private const val TAG = "HomeScreenViewModel"

class HomeScreenViewModel(
    private val repository: WallpaperRepository
) : ViewModel() {

    private val _images = MutableStateFlow<List<UnsplashImage>>(emptyList())
    val images = _images.asStateFlow()

    private val _snackBarEvent = MutableSharedFlow<SnackBarEvent>()
    val snackBarEvent = _snackBarEvent.asSharedFlow()

    val favouriteImageIds: StateFlow<List<String>> = repository.getFavouritesImageIds()
        .catch { exception->
            Log.e(TAG, "Error fetching favourite images: ${exception.message}")
        }
        .stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )


    init {
        fetchImages()
    }

    private fun fetchImages() {
        viewModelScope.launch {
            repository.getFeedImages().onSuccess {
                _images.value = it
                Log.d(TAG, "images: $images")
            }.onError {
                val message = NetworkErrorToMessageMapper(it)
                _snackBarEvent.emit(SnackBarEvent(message))
            }
        }
    }

    fun toggleFavouriteImage(image: UnsplashImage) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.toggleFavouriteStatus(image)
        }
    }


}