package com.binit.zenwalls.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.binit.zenwalls.data.utils.Constants

@Entity(tableName = Constants.REMOTE_KEYS)
data class UnsplashRemoteKeys(
    @PrimaryKey
    val id: String,
    val prevPage: Int?,
    val nextPage: Int?
)
