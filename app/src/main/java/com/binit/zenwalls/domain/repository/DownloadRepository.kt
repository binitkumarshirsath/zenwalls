package com.binit.zenwalls.domain.repository

import com.binit.zenwalls.domain.networkUtil.NetworkError
import com.binit.zenwalls.domain.networkUtil.Result
import com.binit.zenwalls.ui.screens.wallpaper.IMAGE_QUALITY

interface DownloadRepository {
    suspend fun downloadImage(
        imageUrl:String,
        fileName: String
    ): Boolean
}