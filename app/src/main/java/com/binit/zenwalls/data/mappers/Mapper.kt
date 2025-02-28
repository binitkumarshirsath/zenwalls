package com.binit.zenwalls.data.mappers

import com.binit.zenwalls.data.remote.dto.UnsplashImageDTO
import com.binit.zenwalls.domain.model.UnsplashImage

fun UnsplashImageDTO.toUnsplashImage():UnsplashImage{
    return UnsplashImage(
        id = id,
        imageUrlSmall = urls?.small,
        imageUrlRegular = urls?.regular,
        imageUrlRaw = urls?.raw,
        photographerName = user?.name,
        photographerUsername = user?.username,
        photographerProfileImgUrl = user?.profileImage?.large,
        photographerProfileLink = user?.links?.html,
        width = width,
        height = height,
        description = description
    )
}