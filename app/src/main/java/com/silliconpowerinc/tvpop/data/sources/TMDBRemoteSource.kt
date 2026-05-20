package com.silliconpowerinc.tvpop.data.sources

import com.silliconpowerinc.tvpop.common.Secrets
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.annotation.Singleton


private const val BASE_URL = "https://api.themoviedb.org/3"
private const val TIMEOUT_MS: Long = 15000

/**
 * Remote data source for interacting with The Movie Database (TMDB) API.
 * Uses Ktor's [HttpClient] with OkHttp engine to perform network requests.
 */
@Singleton
class TMDBRemoteSource {

    val client = HttpClient(OkHttp) {
        engine {
            addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer ${Secrets.TMDB_API_KEY}")
                    .addHeader("accept", "application/json")
                    .build()
                chain.proceed(request)
            }
        }

        install(ContentNegotiation) {
            // Applies JSON deserialization to the response body
            json(Json {
                ignoreUnknownKeys = true
                prettyPrint = true
            })
        }

        install(HttpTimeout) {
            requestTimeoutMillis = TIMEOUT_MS
            connectTimeoutMillis = TIMEOUT_MS
            socketTimeoutMillis = TIMEOUT_MS
        }

        install(Logging) {
            logger = Logger.DEFAULT
        }
    }

    /**
     * Fetches the list of TV shows from TMDB ordered by popularity, from the `/3/tv/popular` endpoint.
     * @param language The language code to fetch the information in. Defaults to `en-US`.
     * @param page The page number to fetch, starting at 1.
     * @return The deserialized response from the API, containing the list of TV shows and pagination information.
     */
    suspend fun getTVShows(language: String = "en-US", page: Int): TMDBResponse {
        return client.get("$BASE_URL/tv/popular?language=${language}&page=${page}")
            .body()
    }
}