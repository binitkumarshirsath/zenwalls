package com.binit.zenwalls.data.network

import com.binit.zenwalls.BuildConfig
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.ANDROID
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object HttpClientFactory {
    fun create(engine: HttpClientEngine): HttpClient {
        val client = HttpClient(engine = engine) {
            install(Logging) {
                logger = Logger.ANDROID
                level = LogLevel.ALL
            }
            install(ContentNegotiation) {
                json(
                    json = Json {
                        ignoreUnknownKeys = true
                        prettyPrint = true
                        coerceInputValues = true
                    }
                )
            }

            install(HttpTimeout){
                requestTimeoutMillis = 1000
                connectTimeoutMillis = 3000
                socketTimeoutMillis = 1000
            }

            install(HttpRequestRetry){
                retryOnServerErrors(maxRetries = 5)
                exponentialDelay()
            }

            defaultRequest {
                contentType(ContentType.Application.Any)
                header(HttpHeaders.Authorization, "Client-ID ${BuildConfig.UNSPLASH_ACCESS_KEY}")
            }

        }
        return client
    }
}