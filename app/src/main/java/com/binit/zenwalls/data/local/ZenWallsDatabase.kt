package com.binit.zenwalls.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.binit.zenwalls.data.local.dao.FavouriteImagesDao
import com.binit.zenwalls.data.local.entities.FavouriteImageEntity


@Database(
    entities = [
        FavouriteImageEntity::class,
    ],
    exportSchema = false,
    version = 1
)
abstract class ZenWallsDatabase : RoomDatabase() {
    abstract fun favouriteImagesDao(): FavouriteImagesDao
}