package com.binit.zenwalls.data.mappers

import com.binit.zenwalls.data.local.entities.FavouriteImageEntity
import com.binit.zenwalls.data.remote.dto.UnsplashImageDTO
import com.binit.zenwalls.domain.model.UnsplashImage

fun UnsplashImageDTO.toUnsplashImage():UnsplashImage{
    return UnsplashImage(
        id = id,
        imageUrlSmall = urls?.small,
        imageUrlRegular = urls?.regular,
        imageUrlRaw = urls?.raw,
        imageUrlFull = urls?.full,
        photographerName = user?.name,
        photographerUsername = user?.username,
        photographerProfileImgUrl = user?.profileImage?.large,
        photographerProfileLink = user?.links?.html,
        width = width,
        height = height,
        imageColor = color,
        description = description
    )
}

fun FavouriteImageEntity.toUnsplashImage():UnsplashImage{
    return UnsplashImage(
        id = id,
        imageUrlSmall = imageUrlSmall,
        imageUrlRegular = imageUrlRegular,
        imageUrlRaw = imageUrlRaw,
        imageUrlFull = imageUrlFull,
        photographerName = photographerName,
        photographerUsername = photographerUsername,
        photographerProfileImgUrl = photographerProfileImgUrl,
        photographerProfileLink = photographerProfileImgUrl,
        width = width,
        height = height,
        imageColor = imageColor,
        description = description
    )
}

fun UnsplashImage.toFavouriteImageEntity():FavouriteImageEntity{
    return FavouriteImageEntity(
        id = id,
        imageUrlSmall = imageUrlSmall,
        imageUrlRegular = imageUrlRegular,
        imageUrlRaw = imageUrlRaw,
        imageUrlFull = imageUrlFull,
        photographerName = photographerName,
        photographerUsername = photographerUsername,
        photographerProfileImgUrl = photographerProfileImgUrl,
        photographerProfileLink = photographerProfileImgUrl,
        width = width,
        height = height,
        imageColor = imageColor,
        description = description
    )
}