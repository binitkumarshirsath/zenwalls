package com.binit.zenwalls.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.binit.zenwalls.data.local.dao.FavouriteImagesDao
import com.binit.zenwalls.data.local.dao.HomescreenImagesDao
import com.binit.zenwalls.data.local.entities.FavouriteImageEntity
import com.binit.zenwalls.data.local.entities.HomescreenImageEntity
import com.binit.zenwalls.data.local.entities.UnsplashRemoteKeys


@Database(
    entities = [
        FavouriteImageEntity::class,
        HomescreenImageEntity::class,
        UnsplashRemoteKeys::class,
    ],
    exportSchema = false,
    version = 2
)
abstract class ZenWallsDatabase : RoomDatabase() {

    abstract fun favouriteImagesDao(): FavouriteImagesDao

    abstract fun homescreenImagesDao(): HomescreenImagesDao
}