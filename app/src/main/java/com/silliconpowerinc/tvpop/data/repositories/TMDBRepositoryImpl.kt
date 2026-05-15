package com.silliconpowerinc.tvpop.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.silliconpowerinc.tvpop.data.sources.TVShowPagingSource
import com.silliconpowerinc.tvpop.domain.models.TVShow
import com.silliconpowerinc.tvpop.domain.repositories.TMDBRepository
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Singleton

@Singleton
class TMDBRepositoryImpl : TMDBRepository {
    /**
     * Provides a PagingData flow with the list of TV shows from TMDB ordered by popularity,
     * fetched from the `/3/tv/popular` endpoint of the TMDB API using pagination.
     * @param language The language code to fetch the information in. Defaults to `en-US`.
     * @return Flow of PagingData with the list of TV shows returned by the API.
     */
    override fun getTVShowsFlow(language: String): Flow<PagingData<TVShow>> =
        Pager(
            config = PagingConfig(
                // Defined by TMDB's API
                pageSize = 20,
                // Items that are still loading will be null
                enablePlaceholders = true
            ),
            pagingSourceFactory = {
                TVShowPagingSource(language = language)
            }
        )
            .flow
}
