package com.silliconpowerinc.tvpop.data.repositories

import com.silliconpowerinc.tvpop.data.sources.TMDBRemoteSource
import com.silliconpowerinc.tvpop.domain.models.repositories.TMDBRepository

class TMDBRepositoryImpl(
    private val tmdbRemoteSource: TMDBRemoteSource
) : TMDBRepository {
    /**
     * Fetches the list of TV shows from TMDB ordered by popularity, from the `/3/tv/popular` endpoint.
     * @param language The language code to fetch the information in. Defaults to `en-US`.
     * @param page The page number to fetch, starting at 1.
     * @return The deserialized response from the API with the list of TV shows.
     */
    override suspend fun getTVShows(language: String, page: Int) =
        tmdbRemoteSource.getTVShows(language, page).results
}