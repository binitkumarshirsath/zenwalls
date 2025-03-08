package com.binit.zenwalls.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.binit.zenwalls.data.local.entities.FavouriteImageEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface FavouriteImagesDao {

    @Query("Select * from favourite_images")
    fun getAllFavouriteImages():PagingSource<Int,FavouriteImageEntity>

    @Upsert
    suspend fun insertFavouriteImage(image: FavouriteImageEntity)

    @Delete
    suspend fun deleteFavouriteImage(image: FavouriteImageEntity)

    @Query("Select Exists(Select 1 from favourite_images where id=:imageId)")
     fun isImageFavourite(imageId:String):Boolean

    @Query("Select id from favourite_images")
    fun getAllFavouriteImageIds(): Flow<List<String>>
}