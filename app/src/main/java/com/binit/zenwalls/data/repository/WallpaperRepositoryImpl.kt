package com.binit.zenwalls.data.repository

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.binit.zenwalls.data.local.ZenWallsDatabase
import com.binit.zenwalls.data.local.dao.FavouriteImagesDao
import com.binit.zenwalls.data.mappers.toFavouriteImageEntity
import com.binit.zenwalls.data.mappers.toUnsplashImage
import com.binit.zenwalls.data.network.constructUrl
import com.binit.zenwalls.data.network.safeCall
import com.binit.zenwalls.data.paging.HomescreenRemoteMediator
import com.binit.zenwalls.data.paging.SearchPagingSource
import com.binit.zenwalls.data.remote.dto.UnsplashImageDTO
import com.binit.zenwalls.data.remote.dto.UnsplashImagesSearchResult
import com.binit.zenwalls.domain.model.UnsplashImage
import com.binit.zenwalls.domain.networkUtil.NetworkError
import com.binit.zenwalls.domain.networkUtil.Result
import com.binit.zenwalls.domain.networkUtil.map
import com.binit.zenwalls.domain.repository.WallpaperRepository
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private const val TAG = "WallpaperRepositoryImpl"

class WallpaperRepositoryImpl(
    private val httpClient: HttpClient,
    private val favouriteImagesDao: FavouriteImagesDao,
    private val db: ZenWallsDatabase,
//    private val repo:WallpaperRepository, -->creates circular dependency
) : WallpaperRepository {


    override suspend fun getFeedImages(
        page: Int,
        perPage: Int
    ): Result<List<UnsplashImage>, NetworkError> {
        return safeCall<List<UnsplashImageDTO>> {
            httpClient.get(urlString = constructUrl("/photos")) {
                parameter(key = "page", value = page)
                parameter(key = "per_page", value = perPage)
            }
        }.map {
            it.map { unsplashImageDTO ->
                unsplashImageDTO.toUnsplashImage()
            }
        }
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun getFeedImagesPaging(): Flow<PagingData<UnsplashImage>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
            ),
            remoteMediator = HomescreenRemoteMediator(repo = this,
                db = db),
            pagingSourceFactory = {
                db.homescreenImagesDao().getAllHomescreenFeedImages()
            }
        ).flow.map {pagingData->
            pagingData.map {
                it.toUnsplashImage()
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

    override suspend fun searchImage(
        query: String,
        page: Int,
        perPage: Int
    ): Result<List<UnsplashImage>, NetworkError> {
        Log.d(TAG, "🔍 Searching for: $query , page: $page, perPage: $perPage")
        return safeCall<UnsplashImagesSearchResult> {
            httpClient.get(urlString = constructUrl("/search/photos?query=$query")) {
                parameter(key = "page", value = page)
                parameter(key = "per_page", value = perPage)
            }
        }.map {
            it.images.map { unsplashImageDTO ->
                unsplashImageDTO.toUnsplashImage()
            }
        }
    }

    override fun searchImagesPaging(query: String): Flow<PagingData<UnsplashImage>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                initialLoadSize = 30,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                Log.d(TAG, "this $this")
                SearchPagingSource(query, this)
            }
        ).flow
    }

    override suspend fun toggleFavouriteStatus(image: UnsplashImage) {
        val isFavourite = favouriteImagesDao.isImageFavourite(image.id)
        if (isFavourite) {
            favouriteImagesDao.deleteFavouriteImage(image.toFavouriteImageEntity())
            Log.d(TAG, "Deleted image from db")
        } else {
            favouriteImagesDao.insertFavouriteImage(image.toFavouriteImageEntity())
            Log.d(TAG, "Upsert image to db")
        }
    }

    override fun getFavouritesImageIds(): Flow<List<String>> {
        return favouriteImagesDao.getAllFavouriteImageIds()
    }

    override fun getAllFavouritesImages(): Flow<PagingData<UnsplashImage>> {
        return Pager(
            config = PagingConfig(
                initialLoadSize = 10,
                pageSize = 10
            ),
            pagingSourceFactory = {
                favouriteImagesDao.getAllFavouriteImages()
            }
        ).flow
            .map { pagingData ->
                pagingData.map {
                    it.toUnsplashImage()
                }
            }
    }
}


