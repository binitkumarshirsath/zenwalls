package com.binit.zenwalls.ui.screens.wallpaper_list

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.binit.zenwalls.domain.model.UnsplashImage
import com.binit.zenwalls.domain.networkUtil.map
import com.binit.zenwalls.domain.networkUtil.onSuccess
import com.binit.zenwalls.domain.repository.WallpaperRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

private const val TAG = "HomeScreenViewModel"

class HomeScreenViewModel(
    private val repository: WallpaperRepository
):ViewModel(){

    private val _images = MutableStateFlow<List<UnsplashImage>>(emptyList())
    val images = _images.asStateFlow()
    init {
        fetchImages()
    }
    private fun fetchImages(){
        viewModelScope.launch {
            repository.getFeedImages().onSuccess {
                _images.value = it
                Log.d(TAG,"images: $images")
            }
        }
    }
}