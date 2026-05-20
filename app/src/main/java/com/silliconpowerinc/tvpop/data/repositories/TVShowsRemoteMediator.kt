package com.silliconpowerinc.tvpop.data.repositories

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.silliconpowerinc.tvpop.data.sources.TMDBRemoteSource
import com.silliconpowerinc.tvpop.data.sources.TVShowRemoteKeys
import com.silliconpowerinc.tvpop.data.sources.TVShowsLocalSource
import com.silliconpowerinc.tvpop.data.utils.ConnectivityObserver
import com.silliconpowerinc.tvpop.domain.models.TVShow

private const val CACHE_TIMEOUT_MINUTES = 15
private const val CACHE_TIMEOUT_MS = CACHE_TIMEOUT_MINUTES * 60 * 1000

/** Starting value for TMDB API's pagination */
private const val FIRST_PAGE = 1

/**
 * A [RemoteMediator] that handles fetching TV show data from the TMDB API and caching it in the local database.
 * It invalidates the cache after a specified timeout ([CACHE_TIMEOUT_MS]) and offers offline access to cached data.
 *
 * @property tvShowsLocalSource The local data source (database) to store and retrieve TV shows.
 * @property tmdbRemoteSource The remote data source to fetch TV shows from the TMDB API.
 * @property connectivityObserver An observer to monitor the device's network connectivity status.
 * @property language The language code used for fetching TV show information from the TMDB API.
 */
@OptIn(ExperimentalPagingApi::class)
class TVShowsRemoteMediator(
    private val tvShowsLocalSource: TVShowsLocalSource,
    private val tmdbRemoteSource: TMDBRemoteSource,
    private val connectivityObserver: ConnectivityObserver,
    private val language: String
) : RemoteMediator<Int, TVShow>() {
    val tvShowDao = tvShowsLocalSource.tvShowDao()
    val remoteKeysDao = tvShowsLocalSource.remoteKeysDao()

    override suspend fun initialize(): InitializeAction {
        val currentlyCachedLanguage =
            remoteKeysDao.getLanguage() ?: return InitializeAction.LAUNCH_INITIAL_REFRESH
        if (currentlyCachedLanguage != language) return InitializeAction.LAUNCH_INITIAL_REFRESH

        val currentTime = System.currentTimeMillis()
        val lastUpdated =
            remoteKeysDao.getLastUpdated() ?: return InitializeAction.LAUNCH_INITIAL_REFRESH
        val timeSinceLastUpdate = currentTime - lastUpdated

        val isNetworkAvailable = connectivityObserver.isNetworkAvailable

        if (!isNetworkAvailable || timeSinceLastUpdate <= CACHE_TIMEOUT_MS) return InitializeAction.SKIP_INITIAL_REFRESH

        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, TVShow>
    ): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> FIRST_PAGE
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextKey = remoteKeys?.nextKey
                        ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                    nextKey
                }
            }

            val response = tmdbRemoteSource.getTVShows(page = page, language = language)
            val isLastPage = response.page >= response.totalPages

            tvShowsLocalSource.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    remoteKeysDao.deleteAll()
                    tvShowDao.deleteAll()
                }

                val prevKey = if (page == 1) null else page - 1
                val nextKey = if (isLastPage) null else page + 1
                val keys = response.results.map {
                    TVShowRemoteKeys(
                        showId = it.id,
                        prevKey = prevKey,
                        nextKey = nextKey,
                        language = language
                    )
                }

                remoteKeysDao.insertAll(keys)
                tvShowDao.insertAll(response.results)
            }

            MediatorResult.Success(endOfPaginationReached = isLastPage)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, TVShow>): TVShowRemoteKeys? {
        val lastLoadedPage = state.pages.lastOrNull { it.data.isNotEmpty() }
        val lastItemInLastLoadedPage = lastLoadedPage?.data?.lastOrNull()
        return lastItemInLastLoadedPage?.let { tvShow ->
            remoteKeysDao.remoteKeys(showId = tvShow.id)
        }
    }
}