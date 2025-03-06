package com.binit.zenwalls.data.repository

import android.app.DownloadManager
import android.content.Context
import android.os.Environment
import android.util.Log
import androidx.core.net.toUri
import com.binit.zenwalls.domain.repository.DownloadRepository


private const val TAG = "DownloadRepoImpl"

class DownloadRepoImpl(
    private val context: Context
) : DownloadRepository {

    private val downloadManager =
        context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

    override suspend fun downloadImage(
        imageUrl: String,
        fileName: String
    ): Boolean {
        try {
            val request = DownloadManager
                .Request(imageUrl.toUri())
                .setMimeType("image/*")
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setTitle(fileName)
                .setDestinationInExternalPublicDir(
                    Environment.DIRECTORY_PICTURES,
                    fileName
                )

            val queue = downloadManager.enqueue(request)
            Log.d(TAG,"Queue: $queue")
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }

}