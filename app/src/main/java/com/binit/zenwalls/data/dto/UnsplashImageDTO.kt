package com.binit.zenwalls.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UnsplashImageDTO(
    val id: String,
    @SerialName("created_at")
    val createdAt: String?,
    @SerialName("updated_at")
    val updatedAt: String?,
    val width: Int?,
    val height: Int?,
    val color: String?,
    val likes: Int?,
    val description: String?,
    val urls: UrlDTO?,
    val user: UserDTO?
)

@Serializable
data class UserDTO(
    val id: String,
    val username: String?,
    val name: String?,
    val bio: String?,
    @SerialName("profile_image")
    val profileImage: ProfileImageDTO?,
    val links: UserLinksDTO?
)

@Serializable
data class UserLinksDTO(
    val self: String?,
    val html: String?,
    val photos: String?,
    val likes: String?,
)

@Serializable
data class ProfileImageDTO(
    val small: String?,
    val medium: String?,
    val large: String?
)

@Serializable
data class UrlDTO(
    val raw: String?,
    val full: String?,
    val regular: String?,
    val small: String?,
    val thumb: String?
)