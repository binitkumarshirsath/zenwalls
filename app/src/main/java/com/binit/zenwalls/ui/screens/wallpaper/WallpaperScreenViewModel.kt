package com.binit.zenwalls.ui.screens.wallpaper

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.binit.zenwalls.domain.model.UnsplashImage
import com.binit.zenwalls.domain.networkUtil.onSuccess
import com.binit.zenwalls.domain.repository.WallpaperRepository
import com.binit.zenwalls.ui.navigation.Routes
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

private const val TAG = "WallpaperScreenViewModel"

class WallpaperScreenViewModel(
    private val repository: WallpaperRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {


    val imageId: String = savedStateHandle.toRoute<Routes.WallpaperScreen>().wallpaperId
    private var _image = MutableStateFlow<UnsplashImage?>(null)
    val image = _image.onStart {
        getImage()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = null
    )


    private fun getImage() {
        viewModelScope.launch {
            repository.getImage(imageId).onSuccess {
                _image.value = it
            }
        }
    }

}