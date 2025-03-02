package com.binit.zenwalls.data.repository

import com.binit.zenwalls.data.mappers.toUnsplashImage
import com.binit.zenwalls.data.network.constructUrl
import com.binit.zenwalls.data.network.safeCall
import com.binit.zenwalls.data.remote.dto.UnsplashImageDTO
import com.binit.zenwalls.domain.model.UnsplashImage
import com.binit.zenwalls.domain.networkUtil.NetworkError
import com.binit.zenwalls.domain.networkUtil.Result
import com.binit.zenwalls.domain.networkUtil.map
import com.binit.zenwalls.domain.repository.WallpaperRepository
import io.ktor.client.HttpClient
import io.ktor.client.request.get

class WallpaperRepositoryImpl(
    private val httpClient: HttpClient
) : WallpaperRepository {
    override suspend fun getFeedImages(): Result<List<UnsplashImage>, NetworkError> {
        return safeCall<List<UnsplashImageDTO>> {
            httpClient.get(urlString = constructUrl("/photos"))
        }.map {
            it.map { unsplashImageDTO ->
                unsplashImageDTO.toUnsplashImage()
            }
        }
    }

    override suspend fun getImage(imageId: String): Result<UnsplashImage, NetworkError> {
        return safeCall<UnsplashImageDTO> {
            httpClient.get(urlString = constructUrl("/photos/$imageId"))
        }.map {
            it.toUnsplashImage()
        }
    }

}
