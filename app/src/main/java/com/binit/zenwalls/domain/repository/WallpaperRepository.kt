package com.binit.zenwalls.domain.repository

import com.binit.zenwalls.domain.model.UnsplashImage
import com.binit.zenwalls.domain.networkUtil.NetworkError
import com.binit.zenwalls.domain.networkUtil.Result

interface WallpaperRepository {
    suspend fun getFeedImages(): Result<List<UnsplashImage>, NetworkError>
    suspend fun getImage(imageId: String): Result<UnsplashImage, NetworkError>
}