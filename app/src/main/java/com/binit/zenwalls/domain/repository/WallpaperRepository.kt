package com.binit.zenwalls.domain.repository

import androidx.paging.PagingData
import com.binit.zenwalls.data.remote.dto.UnsplashImagesSearchResult
import com.binit.zenwalls.domain.model.UnsplashImage
import com.binit.zenwalls.domain.networkUtil.NetworkError
import com.binit.zenwalls.domain.networkUtil.Result
import kotlinx.coroutines.flow.Flow

interface WallpaperRepository {
    suspend fun getFeedImages(): Result<List<UnsplashImage>, NetworkError>
    suspend fun getImage(imageId: String): Result<UnsplashImage, NetworkError>
    suspend fun searchImage(
        query: String,
        page: Int,
        perPage: Int,
    ): Result<List<UnsplashImage>, NetworkError>
    fun searchImagesPaging(query:String): Flow<PagingData<UnsplashImage>>
}