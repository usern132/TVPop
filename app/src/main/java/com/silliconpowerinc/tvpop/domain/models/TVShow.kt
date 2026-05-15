package com.silliconpowerinc.tvpop.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TVShow(
    val id: Int,
    val name: String,
    @SerialName("backdrop_path")
    val backdropPath: String,
    @SerialName("first_air_date")
    val firstAirDate: String,
    @SerialName("genre_ids")
    val genreIds: List<Int>,
    @SerialName("origin_country")
    val originCountry: List<String>,
    @SerialName("original_language")
    val originalLanguage: String,
    @SerialName("original_name")
    val originalName: String,
    val overview: String,
    val popularity: Double,
    @SerialName("poster_path")
    val posterPath: String,
    @SerialName("vote_average")
    val voteAverage: Double,
    @SerialName("vote_count")
    val voteCount: Int
) {
    companion object {
        val examples: List<TVShow> = listOf(
            TVShow(
                id = 1,
                name = "Example Show",
                backdropPath = "/example_backdrop.jpg",
                firstAirDate = "2024-01-01",
                genreIds = listOf(18, 10765),
                originCountry = listOf("US"),
                originalLanguage = "en",
                originalName = "Example Show Original",
                overview = "This is an example TV show used for testing and previews.",
                popularity = 123.45,
                posterPath = "/example_poster.jpg",
                voteAverage = 8.5,
                voteCount = 1000
            ),
            TVShow(
                id = 2,
                name = "Another Show",
                backdropPath = "/another_backdrop.jpg",
                firstAirDate = "2023-05-15",
                genreIds = listOf(35, 80),
                originCountry = listOf("UK"),
                originalLanguage = "en",
                originalName = "Another Show Original",
                overview = "This is another example TV show used for testing and previews.",
                popularity = 98.76,
                posterPath = "/another_poster.jpg",
                voteAverage = 7.8,
                voteCount = 500
            ),
            TVShow(
                id = 3,
                name = "Sample Show",
                backdropPath = "/sample_backdrop.jpg",
                firstAirDate = "2022-10-10",
                genreIds = listOf(10759, 9648),
                originCountry = listOf("CA"),
                originalLanguage = "en",
                originalName = "Sample Show Original",
                overview = "This is a sample TV show used for testing and previews.",
                popularity = 75.32,
                posterPath = "/sample_poster.jpg",
                voteAverage = 6.9,
                voteCount = 250
            ),
            TVShow(
                id = 4,
                name = "Test Show",
                backdropPath = "/test_backdrop.jpg",
                firstAirDate = "2021-08-20",
                genreIds = listOf(16, 10751),
                originCountry = listOf("JP"),
                originalLanguage = "ja",
                originalName = "Test Show Original",
                overview = "This is a test TV show used for testing and previews.",
                popularity = 50.12,
                posterPath = "/test_poster.jpg",
                voteAverage = 5.5,
                voteCount = 150
            ),
            TVShow(
                id = 5,
                name = "Demo Show",
                backdropPath = "/demo_backdrop.jpg",
                firstAirDate = "2020-03-30",
                genreIds = listOf(99, 10764),
                originCountry = listOf("FR"),
                originalLanguage = "fr",
                originalName = "Demo Show Original",
                overview = "This is a demo TV show used for testing and previews.",
                popularity = 25.67,
                posterPath = "/demo_poster.jpg",
                voteAverage = 4.3,
                voteCount = 75
            )
        )
    }
}
