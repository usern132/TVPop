package com.silliconpowerinc.tvpop.data.repositories

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.silliconpowerinc.tvpop.data.sources.TMDBRemoteSource
import com.silliconpowerinc.tvpop.data.sources.TVShowRemoteKeys
import com.silliconpowerinc.tvpop.data.sources.TVShowsLocalSource
import com.silliconpowerinc.tvpop.domain.models.TVShow

private const val CACHE_TIMEOUT_MINUTES = 5
private const val CACHE_TIMEOUT_MS = CACHE_TIMEOUT_MINUTES * 60 * 1000

@OptIn(ExperimentalPagingApi::class)
class TVShowsRemoteMediator(
    private val tvShowsLocalSource: TVShowsLocalSource,
    private val tmdbRemoteSource: TMDBRemoteSource
) : RemoteMediator<Int, TVShow>() {

    val tvShowDao = tvShowsLocalSource.tvShowDao()
    val remoteKeysDao = tvShowsLocalSource.remoteKeysDao()

    override suspend fun initialize(): InitializeAction {
        val currentTime = System.currentTimeMillis()
        val lastUpdated =
            remoteKeysDao.getLastUpdated() ?: return InitializeAction.LAUNCH_INITIAL_REFRESH

        val timeSinceLastUpdate = currentTime - lastUpdated
        return if (timeSinceLastUpdate <= CACHE_TIMEOUT_MS) {
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, TVShow>
    ): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextKey = remoteKeys?.nextKey
                        ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                    nextKey
                }
            }

            val response = tmdbRemoteSource.getTVShows(page = page)
            val isLastPage = response.page >= response.totalPages

            tvShowsLocalSource.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    remoteKeysDao.deleteAll()
                    tvShowDao.deleteAll()
                }

                val prevKey = if (page == 1) null else page - 1
                val nextKey = if (isLastPage) null else page + 1
                val keys = response.results.map {
                    TVShowRemoteKeys(showId = it.id, prevKey = prevKey, nextKey = nextKey)
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