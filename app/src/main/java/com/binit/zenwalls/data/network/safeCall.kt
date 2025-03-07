package com.binit.zenwalls.data.network

import com.binit.zenwalls.domain.networkUtil.NetworkError
import io.ktor.client.statement.HttpResponse
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.GlobalScope.coroutineContext
import kotlinx.coroutines.ensureActive
import kotlinx.serialization.SerializationException
import com.binit.zenwalls.domain.networkUtil.Result
import io.ktor.client.plugins.HttpRequestTimeoutException
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.delay
import responseToResult
import kotlin.math.pow

@OptIn(DelicateCoroutinesApi::class)
suspend inline fun <reified T> safeCall(
    maxRetries: Int = 3,
    initialDelayMillis: Long = 500L, // Initial delay of 500ms
    execute: () -> HttpResponse
): Result<T, NetworkError> {
    var attempt = 0

    while (attempt <= maxRetries) {
        try {
            coroutineContext.ensureActive() // Ensure coroutine is still active
            val response = execute()
            return responseToResult(response)
        } catch (e: UnresolvedAddressException) {
            return Result.Error(NetworkError.NO_INTERNET_CONNECTION)
        } catch (e: SerializationException) {
            return Result.Error(NetworkError.SERIALISATION_ERROR) // No retry for serialization errors
        } catch (e: HttpRequestTimeoutException) {
            if (attempt == maxRetries) return Result.Error(NetworkError.REQUEST_TIMEOUT)
        } catch (e: Exception) {
            if (attempt == maxRetries) return Result.Error(NetworkError.UNKNOWN_ERROR)
        }

        attempt++
        if (attempt <= maxRetries) {
            val delayTime = initialDelayMillis * 2.0.pow(attempt).toLong()
            delay(delayTime) // Exponential backoff delay
        }
    }

    return Result.Error(NetworkError.UNKNOWN_ERROR) // Should not reach here
}
