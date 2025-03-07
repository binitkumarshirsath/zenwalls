package com.binit.zenwalls.ui.screens.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.binit.zenwalls.domain.model.UnsplashImage
import com.binit.zenwalls.domain.repository.WallpaperRepository
import com.binit.zenwalls.domain.util.SnackBarEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


private const val TAG = "SearchScreenViewModel"
class SearchScreenViewModel(
    private val repository: WallpaperRepository
) : ViewModel() {

    private val _query = MutableStateFlow<String>("")
    val query : StateFlow<String> = _query.asStateFlow()



    private val _images = MutableStateFlow<PagingData<UnsplashImage>>(PagingData.empty())
    val images: Flow<PagingData<UnsplashImage>> = _images.asStateFlow()


    fun setSearchQuery(newQuery: String) {
        _query.value = newQuery
    }

    fun searchImages(){
        val currentQuery = _query.value.trim()
        Log.d(TAG,"🔍 Searching for: $currentQuery")
        if(currentQuery.isNotEmpty()){
            viewModelScope.launch {
                repository
                    .searchImagesPaging(currentQuery)
                    .cachedIn(viewModelScope)
                    .collect{
                        _images.value = it
                    }
            }
        }
    }


}