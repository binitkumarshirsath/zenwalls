package com.binit.zenwalls.ui.screens.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.binit.zenwalls.domain.model.UnsplashImage
import com.binit.zenwalls.domain.repository.WallpaperRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


private const val TAG = "SearchScreenViewModel"
class SearchScreenViewModel(
    private val repository: WallpaperRepository
) : ViewModel() {

    private val _query = MutableStateFlow<String>("")
    val query : StateFlow<String> = _query.asStateFlow()

    val favouriteImageIds: StateFlow<List<String>> = repository.getFavouritesImageIds()
        .catch { exception->
            Log.e(TAG, "Error fetching favourite images: ${exception.message}")
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

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

    fun toggleFavouriteImage(image: UnsplashImage) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.toggleFavouriteStatus(image)
        }
    }
}