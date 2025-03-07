package com.binit.zenwalls.data.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.binit.zenwalls.domain.model.UnsplashImage
import com.binit.zenwalls.domain.networkUtil.Result
import com.binit.zenwalls.domain.networkUtil.onSuccess
import com.binit.zenwalls.domain.repository.WallpaperRepository

private const val TAG = "SearchPagingSource"

class SearchPagingSource(
    private val query: String,
    private val repo: WallpaperRepository
) : PagingSource<Int, UnsplashImage>() {
    override fun getRefreshKey(state: PagingState<Int, UnsplashImage>): Int? {
        return state.anchorPosition
    }

    private val STARTING_PAGE_INDEX = 1
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UnsplashImage> {
        Log.d(TAG,"LoadParams: $params")
        val currentPage = params.key ?: STARTING_PAGE_INDEX
        val perPage = params.loadSize

        Log.d(TAG,"🔍 Loading page: $currentPage with size: $perPage")

        val result = repo.searchImage(query, currentPage, perPage)

        return when (result) {
            is Result.Error -> {
                LoadResult.Error(
                    Throwable(result.error.toString())
                )
            }
            is Result.Success -> {
                LoadResult
                    .Page(
                        data = result.data,
                        prevKey = if (currentPage == STARTING_PAGE_INDEX) null else currentPage - 1,
                        nextKey = if (result.data.isEmpty()) null else currentPage + 1
                    )
            }
        }
    }

}