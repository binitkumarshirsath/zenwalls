package com.binit.zenwalls.ui.screens.wallpaper_list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.binit.zenwalls.domain.model.UnsplashImage
import com.binit.zenwalls.domain.networkUtil.NetworkError
import com.binit.zenwalls.domain.networkUtil.onError
import com.binit.zenwalls.domain.networkUtil.onSuccess
import com.binit.zenwalls.domain.repository.WallpaperRepository
import com.binit.zenwalls.domain.util.SnackBarEvent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

private const val TAG = "HomeScreenViewModel"

class HomeScreenViewModel(
    private val repository: WallpaperRepository
) : ViewModel() {

    private val _images = MutableStateFlow<List<UnsplashImage>>(emptyList())
    val images = _images.asStateFlow()

    private val _snackBarEvent = MutableSharedFlow<SnackBarEvent>()
    val snackBarEvent = _snackBarEvent.asSharedFlow()

    init {
        fetchImages()
    }

    private fun fetchImages() {
        viewModelScope.launch {
            repository.getFeedImages().onSuccess {
                _images.value = it
                Log.d(TAG, "images: $images")
            }.onError {
                val message = when (it) {
                    NetworkError.BAD_REQUEST -> "Bad request. Please try again."
                    NetworkError.UNAUTHORIZED -> "Unauthorized access. Please log in again."
                    NetworkError.FORBIDDEN -> "Access denied. You don't have permission."
                    NetworkError.NOT_FOUND -> "Requested resource not found."
                    NetworkError.NO_INTERNET_CONNECTION -> "No internet connection. Check your network."
                    NetworkError.UNKNOWN_ERROR -> "An unknown error occurred. Please try again later."
                    NetworkError.SERVER_ERROR -> "Server error. Please try again after some time."
                    NetworkError.SERIALISATION_ERROR -> "Data processing error. Please report this issue."
                }
                _snackBarEvent.emit(SnackBarEvent(message))
            }
        }
    }
}