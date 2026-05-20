package com.silliconpowerinc.tvpop.data.sources

import com.silliconpowerinc.tvpop.domain.models.TVShow
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Data class representing the response from the TMDB API for a list of TV shows.
 *
 * @property page The current page number.
 * @property results The list of TV shows on this page.
 * @property totalPages The total number of pages available.
 * @property totalResults The total number of results available across all pages.
 */
@Serializable
data class TMDBResponse(
    val page: Int,
    val results: List<TVShow>,
    @SerialName("total_pages")
    val totalPages: Int,
    @SerialName("total_results")
    val totalResults: Int
)