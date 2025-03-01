package com.binit.zenwalls.ui.screens.wallpaper_list

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.binit.zenwalls.domain.model.UnsplashImage
import com.binit.zenwalls.domain.networkUtil.map
import com.binit.zenwalls.domain.networkUtil.onSuccess
import com.binit.zenwalls.domain.repository.WallpaperRepository
import kotlinx.coroutines.launch

private const val TAG = "HomeScreenViewModel"

class HomeScreenViewModel(
    private val repository: WallpaperRepository
):ViewModel(){

    var images : List<UnsplashImage> by mutableStateOf(emptyList())

    private fun fetchImages(){
        viewModelScope.launch {
            repository.getFeedImages().onSuccess {
                images = it
                Log.d(TAG,"images: $images")
            }
        }
    }
}