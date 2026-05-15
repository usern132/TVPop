package com.silliconpowerinc.tvpop.data.sources

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.silliconpowerinc.tvpop.domain.models.TVShow
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.koin.java.KoinJavaComponent.inject

@Serializable
data class TMDBResponse(
    val page: Int,
    val results: List<TVShow>,
    @SerialName("total_pages")
    val totalPages: Int,
    @SerialName("total_results")
    val totalResults: Int
)


/** Starting value for TMDB API's pagination */
private const val FIRST_PAGE = 1

class TVShowPagingSource(
    val language: String = "en-US"
) : PagingSource<Int, TVShow>() {
    private val tmdbRemoteSource: TMDBRemoteSource by inject(TMDBRemoteSource::class.java)

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TVShow> {
        return try {
            val currentPage = params.key ?: FIRST_PAGE
            val response = tmdbRemoteSource.getTVShows(language = language, page = currentPage)
            val results = response.results.distinctBy { tvShow -> tvShow.id }

            LoadResult.Page(
                data = results,
                prevKey = if (currentPage == FIRST_PAGE) null else currentPage - FIRST_PAGE,
                nextKey = if (currentPage < response.totalPages) currentPage + FIRST_PAGE else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, TVShow>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}
