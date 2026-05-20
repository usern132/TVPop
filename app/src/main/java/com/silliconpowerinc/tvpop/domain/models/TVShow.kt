package com.silliconpowerinc.tvpop.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.text.SimpleDateFormat
import java.util.Date

private const val IMAGE_WIDTH = "w1280"
private const val IMAGES_BASE_URL = "https://image.tmdb.org/t/p/$IMAGE_WIDTH"

/**
 * Domain model representing a TV show, also used as a Room entity and for JSON serialization.
 *
 * @property id Unique identifier for the TV show.
 * @property name Name of the TV show.
 * @property relativeBackdropPath Relative path to the backdrop image on TMDB's servers.
 * @property firstAirDate Date when the show first aired, as a string in "yyyy-MM-dd" format.
 * @property genreIds List of genre IDs associated with the show.
 * @property originCountry List of ISO 3166-1 A-2 country codes where the show is from.
 * @property originalLanguage ISO 639-1:2002 language code of the show's original language.
 * @property originalName Original name of the TV show (untranslated).
 * @property overview A brief description of the show.
 * @property popularity Popularity score assigned by TMDB.
 * @property posterPath Relative path to the poster image on TMDB servers.
 * @property voteAverage Average user rating for the show.
 * @property voteCount Total number of user votes for the show.
 */
@Entity
@Serializable
data class TVShow(
    @PrimaryKey
    val id: Int,
    val name: String?,
    @SerialName("backdrop_path")
    val relativeBackdropPath: String?,
    @SerialName("first_air_date")
    val firstAirDate: String?,
    @SerialName("genre_ids")
    val genreIds: List<Int>?,
    @SerialName("origin_country")
    val originCountry: List<String>?,
    @SerialName("original_language")
    val originalLanguage: String?,
    @SerialName("original_name")
    val originalName: String?,
    val overview: String?,
    val popularity: Double?,
    @SerialName("poster_path")
    val posterPath: String?,
    @SerialName("vote_average")
    val voteAverage: Double?,
    @SerialName("vote_count")
    val voteCount: Int?
) {
    /** Full URL for [relativeBackdropPath]. */
    val backdropUrl: String?
        get() = if (relativeBackdropPath == null) null else "$IMAGES_BASE_URL$relativeBackdropPath"

    /** The [firstAirDate] parsed into a [Date] object. */
    val firstAirDateObject: Date?
        get() {
            if (firstAirDate.isNullOrBlank()) return null
            val formatFromAPI = SimpleDateFormat("yyyy-MM-dd")
            val date = formatFromAPI.parse(this.firstAirDate)
            return date
                ?: throw IllegalArgumentException("firstAirDate could not be parsed into a Date object")
        }

    companion object {
        /** Example [TVShow] instances useful for previews and testing. */
        val examples: List<TVShow> = listOf(
            TVShow(
                id = 1,
                name = "Example Show",
                relativeBackdropPath = "/nn3SuLTO4hum8yAxaY4ql8h6kRk.jpg",
                firstAirDate = "2024-01-01",
                genreIds = listOf(18, 10765),
                originCountry = listOf("US"),
                originalLanguage = "en",
                originalName = "Example Show Original",
                overview = "This is an example TV show used for testing and previews.",
                popularity = 123.45,
                posterPath = "/mBcu8d6x6zB1el3MPNl7cZQEQ31.jpg",
                voteAverage = 8.534,
                voteCount = 1000
            ),
            TVShow(
                id = 2,
                name = "Another Show",
                relativeBackdropPath = "/56v2KjBlU4XaOv9rVYEQypROD7P.jpg",
                firstAirDate = "2023-05-15",
                genreIds = listOf(35, 80),
                originCountry = listOf("UK"),
                originalLanguage = "en",
                originalName = "Another Show Original",
                overview = "This is another example TV show used for testing and previews.",
                popularity = 98.76,
                posterPath = "/mHZSq8LA5Dt48JjaOZ5tcPXQRVN.jpg",
                voteAverage = 7.832,
                voteCount = 500
            ),
            TVShow(
                id = 3,
                name = "Sample Show",
                relativeBackdropPath = "/4AXxajuAz9tHAOe6h5zDg8z1X2s.jpg",
                firstAirDate = "2022-10-10",
                genreIds = listOf(10759, 9648),
                originCountry = listOf("CA"),
                originalLanguage = "en",
                originalName = "Sample Show Original",
                overview = "This is a sample TV show used for testing and previews.",
                popularity = 75.32,
                posterPath = "/eyTu5c8LniVciRZIOSHTvvkkgJa.jpg",
                voteAverage = 6.921,
                voteCount = 250
            ),
            TVShow(
                id = 4,
                name = "Test Show",
                relativeBackdropPath = "/tc7canPSAn2X14hYi6Rl3gZm1o4.jpg",
                firstAirDate = "2021-08-20",
                genreIds = listOf(16, 10751),
                originCountry = listOf("JP"),
                originalLanguage = "ja",
                originalName = "Test Show Original",
                overview = "This is a test TV show used for testing and previews.",
                popularity = 50.12,
                posterPath = "/haJ9eHytVO3H3JooMJG1DiWwDNm.jpg",
                voteAverage = 5.521,
                voteCount = 150
            ),
            TVShow(
                id = 5,
                name = "Demo Show",
                relativeBackdropPath = "/r0Q6eeN9L1ORL9QsV0Sg8ZV3vnv.jpg",
                firstAirDate = "2020-03-30",
                genreIds = listOf(99, 10764),
                originCountry = listOf("FR"),
                originalLanguage = "fr",
                originalName = "Demo Show Original",
                overview = "This is a demo TV show used for testing and previews.",
                popularity = 25.67,
                posterPath = "/3Cz7ySOQJmqiuTdrc6CY0r65yDI.jpg",
                voteAverage = 4.343,
                voteCount = 75
            )
        )
        val nullExample = TVShow(
            id = 1,
            name = null,
            relativeBackdropPath = null,
            firstAirDate = null,
            genreIds = null,
            originCountry = null,
            originalLanguage = null,
            originalName = null,
            overview = null,
            popularity = null,
            posterPath = null,
            voteAverage = null,
            voteCount = null
        )
    }
}
