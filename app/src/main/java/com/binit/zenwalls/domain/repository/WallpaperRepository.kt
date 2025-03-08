package com.binit.zenwalls.domain.repository

import androidx.paging.PagingData
import com.binit.zenwalls.domain.model.UnsplashImage
import com.binit.zenwalls.domain.networkUtil.NetworkError
import com.binit.zenwalls.domain.networkUtil.Result
import kotlinx.coroutines.flow.Flow

interface WallpaperRepository {
    suspend fun getFeedImages(
        page:Int=1,
        perPage:Int=10,
    ): Result<List<UnsplashImage>, NetworkError>

    fun getFeedImagesPaging():Flow<PagingData<UnsplashImage>>

    suspend fun getImage(imageId: String): Result<UnsplashImage, NetworkError>
    suspend fun searchImage(
        query: String,
        page: Int,
        perPage: Int,
    ): Result<List<UnsplashImage>, NetworkError>
    fun searchImagesPaging(query:String): Flow<PagingData<UnsplashImage>>

    suspend fun toggleFavouriteStatus(image:UnsplashImage)

    fun getFavouritesImageIds():Flow<List<String>>

    fun getAllFavouritesImages():Flow<PagingData<UnsplashImage>>
}