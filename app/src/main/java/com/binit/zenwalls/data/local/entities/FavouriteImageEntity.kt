package com.binit.zenwalls.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.binit.zenwalls.data.utils.Constants

@Entity(tableName = Constants.FAVOURITE_IMAGES)
data class FavouriteImageEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val imageUrlSmall: String?,
    val imageUrlRegular: String?,
    val imageUrlFull: String?,
    val imageUrlRaw: String?,
    val imageColor: String?,
    val photographerName: String?,
    val photographerUsername: String?,
    val photographerProfileImgUrl: String?,
    val photographerProfileLink: String?,
    val width: Int?,
    val height: Int?,
    val description: String?
)