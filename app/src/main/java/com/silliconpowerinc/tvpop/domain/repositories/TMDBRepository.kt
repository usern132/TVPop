package com.silliconpowerinc.tvpop.domain.repositories

import androidx.paging.PagingData
import com.silliconpowerinc.tvpop.domain.models.TVShow
import kotlinx.coroutines.flow.Flow

/**
 * Interface for the repository that handles TV show data from TMDB.
 */
interface TMDBRepository {
    /**
     * Provides a [Flow] of [PagingData] containing [TVShow] objects.
     *
     * @param language The language code for fetching TV show details.
     * @return A flow of paginated TV shows.
     */
    fun getTVShowsFlow(language: String): Flow<PagingData<TVShow>>

    /**
     * Retrieves a specific [TVShow] from the repository by its [id] if it exists.
     *
     * @param id The unique identifier of the TV show.
     * @return The [TVShow] if found, or null otherwise.
     */
    fun getTVShow(id: Int): TVShow?

    /**
     * Updates the AI-generated overview for a specific TV show in the repository.
     *
     * @param id The unique identifier of the TV show to update.
     * @param aiOverview The new AI-generated overview text to be stored.
     * @param language The language code for which the overview is being updated.
     */
    suspend fun updateTVShowAIOverview(id: Int, aiOverview: String, language: String)
}
