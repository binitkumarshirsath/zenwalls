package com.binit.zenwalls.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.binit.zenwalls.data.local.entities.HomescreenImageEntity
import com.binit.zenwalls.data.local.entities.UnsplashRemoteKeys


@Dao
interface HomescreenImagesDao {

    @Query("Select * from homescreen_images")
    fun getAllHomescreenFeedImages():PagingSource<Int,HomescreenImageEntity>

    @Upsert
    suspend fun insertHomescreenFeedImages(images:List<HomescreenImageEntity>)

    @Query("Delete from homescreen_images")
    suspend fun deleteHomescreenFeedImages()

    @Query("Select * from remote_keys where id=:id")
    fun getRemoteKeys(id:String):UnsplashRemoteKeys

    @Upsert
    suspend fun insertRemoteKeys(remoteKeys:List<UnsplashRemoteKeys>)

    @Query("DELETE FROM remote_keys")
    suspend fun deleteRemoteKeys()

}