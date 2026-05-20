package com.silliconpowerinc.tvpop.data.repositories

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.silliconpowerinc.tvpop.data.sources.TMDBRemoteSource
import com.silliconpowerinc.tvpop.data.sources.TVShowsLocalSource
import com.silliconpowerinc.tvpop.data.utils.ConnectivityObserver
import com.silliconpowerinc.tvpop.domain.models.TVShow
import com.silliconpowerinc.tvpop.domain.repositories.TMDBRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Singleton

/**
 * Implementation of the [TMDBRepository] interface.
 * Coordinates data retrieval from the local database and the remote TMDB API,
 * and handles caching and offline support.
 *
 * @property tvShowsLocalSource The local data source for TV shows.
 * @property tmdbRemoteSource The remote data source for fetching TV shows from the TMDB API.
 * @property connectivityObserver Observer for network connectivity status.
 */
@Singleton
class TMDBRepositoryImpl(
    private val tvShowsLocalSource: TVShowsLocalSource,
    private val tmdbRemoteSource: TMDBRemoteSource,
    private val connectivityObserver: ConnectivityObserver
) : TMDBRepository {
    private val cachedShows = mutableMapOf<Int, TVShow>()

    /**
     * Provides a PagingData flow with the list of TV shows from TMDB ordered by popularity,
     * fetched from the `/3/tv/popular` endpoint of the TMDB API using pagination.
     * @param language The language code to fetch the information in. Defaults to `en-US`.
     * @return Flow of PagingData with the list of TV shows returned by the API.
     */
    @OptIn(ExperimentalPagingApi::class)
    override fun getTVShowsFlow(language: String): Flow<PagingData<TVShow>> {
        val tvShowDao = tvShowsLocalSource.tvShowDao()
        return Pager(
            config = PagingConfig(
                // Defined by TMDB's API
                pageSize = 20,
                // Items that are still loading will be null
                enablePlaceholders = true
            ),
            remoteMediator = TVShowsRemoteMediator(
                tvShowsLocalSource = tvShowsLocalSource,
                tmdbRemoteSource = tmdbRemoteSource,
                connectivityObserver = connectivityObserver,
                language = language
            )
        ) {
            tvShowDao.pagingSource()
        }.flow.map { pagingData ->
            pagingData.map { tvShow ->
                cachedShows[tvShow.id] = tvShow
                return@map tvShow
            }
        }
    }

    /** Returns a TV show cached from the API's response and stored in a Collection.
     * This function should only be called after the paged data has been retrieved by
     * the UI layer; else, the requested show will be missing.
     *
     * @param id the TV show's ID
     * @return The TV show with the specified ID, if it has been previously retrieved from the API.
     */
    override fun getTVShow(id: Int): TVShow? = cachedShows[id]

    override suspend fun updateTVShowAIOverview(id: Int, aiOverview: String) {
        tvShowsLocalSource.tvShowDao().updateAIOverview(id, aiOverview)
    }
}
