package com.binit.zenwalls.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UnsplashImagesSearchResult(
    @SerialName("results")
    val images: List<UnsplashImageDTO>,
    val total: Int,
    @SerialName("total_pages")
    val totalPages: Int
)