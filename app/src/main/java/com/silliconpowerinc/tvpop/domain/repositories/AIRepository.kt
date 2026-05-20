package com.silliconpowerinc.tvpop.domain.repositories

import com.silliconpowerinc.tvpop.domain.models.TVShow

/**
 * Interface for the repository that handles AI-generated data from an AI service.
 */
interface AIRepository {
    /**
     * Provides an AI-generated overview for a given TV show in the specified language.
     *
     * @param tvShow The TV show for which to generate the overview.
     * @param language The language code for which to generate the overview.
     * @return A string containing the AI-generated overview of the TV show.
     */
    suspend fun generateAIOverview(tvShow: TVShow, language: String): String
}