package com.binit.zenwalls.ui.screens.wallpaper

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.binit.zenwalls.domain.model.UnsplashImage
import com.binit.zenwalls.domain.networkUtil.onSuccess
import com.binit.zenwalls.domain.repository.DownloadRepository
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
    private val downloadRepo: DownloadRepository,
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

    fun downloadImage(imageQuality: IMAGE_QUALITY) {
        val url = when (imageQuality) {
            IMAGE_QUALITY.SMALL -> image.value?.imageUrlSmall
            IMAGE_QUALITY.REGULAR -> image.value?.imageUrlRegular
            IMAGE_QUALITY.HIGH -> image.value?.imageUrlFull
            IMAGE_QUALITY.RAW -> image.value?.imageUrlRaw
        }
        val fileName = _image.value?.description?.take(5) ?: "Untitled_Image_Binit"
        val validFileName = if (fileName.contains(".")) fileName else "$fileName.jpg"
        viewModelScope.launch {
            if (url.isNullOrEmpty().not()) {
                downloadRepo.downloadImage(url!!, validFileName)
            } else {

            }
        }
    }

    private fun getImage() {
        viewModelScope.launch {
            repository.getImage(imageId).onSuccess {
                _image.value = it
            }
        }
    }

}