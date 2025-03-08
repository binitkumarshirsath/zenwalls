package com.binit.zenwalls.data.paging

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.LoadType.APPEND
import androidx.paging.LoadType.PREPEND
import androidx.paging.LoadType.REFRESH
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.binit.zenwalls.data.local.ZenWallsDatabase
import com.binit.zenwalls.data.local.entities.HomescreenImageEntity
import com.binit.zenwalls.data.local.entities.UnsplashRemoteKeys
import com.binit.zenwalls.data.mappers.toHomescreenImageEntity
import com.binit.zenwalls.domain.networkUtil.Result
import com.binit.zenwalls.domain.repository.WallpaperRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


private const val TAG = "HomescreenRemoteMediator"

@OptIn(ExperimentalPagingApi::class)
class HomescreenRemoteMediator(
    private val repo: WallpaperRepository,
    private val db: ZenWallsDatabase,
) : RemoteMediator<Int, HomescreenImageEntity>() {

    companion object {
        private const val STARTING_PAGE_INDEX = 1
    }


    private val homescreenImagesDao = db.homescreenImagesDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, HomescreenImageEntity>
    ): MediatorResult {
        try {
            val currentPage = when (loadType) {
                REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextPage?.minus(1) ?: STARTING_PAGE_INDEX
                }

                PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    Log.d(TAG, "remoteKeysPrev: ${remoteKeys?.prevPage}")
                    val prevPage = remoteKeys?.prevPage
                        ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                    prevPage
                }

                APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    Log.d(TAG, "remoteKeysNext: ${remoteKeys?.nextPage}")
                    val nextPage = remoteKeys?.nextPage
                        ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                    nextPage
                }
            }
            val response = repo.getFeedImages(page = currentPage, perPage = 20)
            val endOfPagination = when (response) {
                is Result.Success -> response.data.isEmpty()
                is Result.Error -> false // Default to false on error
            }

            Log.d(TAG, "endOfPagination: $endOfPagination")

            val prevPage = if (currentPage == 1) null else currentPage - 1
            val nextPage = if (endOfPagination) null else currentPage + 1



            db.withTransaction {
                val remoteKeys = when (response) {
                    is Result.Error -> {
                        emptyList<UnsplashRemoteKeys>()
                    }

                    is Result.Success -> {
                        response.data.map {
                            UnsplashRemoteKeys(
                                id = it.id,
                                prevPage = prevPage,
                                nextPage = nextPage
                            )
                        }
                    }
                }

                val unsplashImages = when (response) {
                    is Result.Success -> {
                        response.data.map {
                            it.toHomescreenImageEntity()
                        }
                    }

                    is Result.Error -> {
                        emptyList()
                    }
                }

                homescreenImagesDao.insertRemoteKeys(remoteKeys)
                homescreenImagesDao.insertHomescreenFeedImages(
                    images = unsplashImages
                )
            }

            return MediatorResult.Success(endOfPaginationReached = endOfPagination)

        } catch (e: Exception) {
            Log.d(TAG, "Exception: ${e.message}")
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, HomescreenImageEntity>
    ): UnsplashRemoteKeys? {
        return withContext(Dispatchers.IO) {  // ✅ Move DB query to IO thread
            state.anchorPosition?.let { position ->
                state.closestItemToPosition(position)?.id?.let { id ->
                    homescreenImagesDao.getRemoteKeys(id = id)
                }
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, HomescreenImageEntity>
    ): UnsplashRemoteKeys? {
        return withContext(Dispatchers.IO) {  // ✅ Move DB query to IO thread
            state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let { unsplashImage ->
                homescreenImagesDao.getRemoteKeys(id = unsplashImage.id)
            }
        }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, HomescreenImageEntity>
    ): UnsplashRemoteKeys? {
        return withContext(Dispatchers.IO) {  // ✅ Move DB query to IO thread
            state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { unsplashImage ->
                homescreenImagesDao.getRemoteKeys(id = unsplashImage.id)
            }
        }
    }

}