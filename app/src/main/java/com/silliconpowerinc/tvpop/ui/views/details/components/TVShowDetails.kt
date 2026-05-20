package com.silliconpowerinc.tvpop.ui.views.details.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.silliconpowerinc.tvpop.R
import com.silliconpowerinc.tvpop.common.toLocalizedCountryNameWithEmoji
import com.silliconpowerinc.tvpop.common.toLocalizedLanguageName
import com.silliconpowerinc.tvpop.domain.models.TVShow
import com.silliconpowerinc.tvpop.ui.common.BORDER_PADDING_DP
import com.silliconpowerinc.tvpop.ui.common.components.MyAsyncImage
import com.silliconpowerinc.tvpop.ui.theme.AppTypography
import com.silliconpowerinc.tvpop.ui.theme.textShadow
import com.silliconpowerinc.tvpop.ui.views.details.AIOverviewState
import com.silliconpowerinc.tvpop.ui.views.details.DetailsScreenState
import com.silliconpowerinc.tvpop.ui.views.details.components.detailcards.AIOverviewDetailCard
import com.silliconpowerinc.tvpop.ui.views.details.components.detailcards.RatingDetailCard
import com.silliconpowerinc.tvpop.ui.views.details.components.detailcards.TitleAndDescriptionDetailCard
import java.text.DateFormat

private const val BACKDROP_WEIGHT = 0.4f

/**
 * Composable that displays the detailed information of a [TVShow].
 * It contains a banner with a backdrop image and a title and a list of cards with the details.
 *
 * @param tvShow The TV show to display details for.
 * @param state The state of the details screen, containing the AI overview loading state.
 */
@Composable
fun TVShowDetails(tvShow: TVShow, state: DetailsScreenState) {
    val textShadow = MaterialTheme.textShadow

    Column {
        Banner(
            modifier = Modifier
                .weight(BACKDROP_WEIGHT)
                .fillMaxWidth(),
            tvShow = tvShow,
            textShadow = textShadow
        )
        DetailsCards(
            modifier = Modifier
                .weight(1 - BACKDROP_WEIGHT)
                .padding(horizontal = BORDER_PADDING_DP.dp),
            tvShow = tvShow,
            state = state
        )
    }
}

private const val SHORT_CARD_HEIGHT_DP = 150
private const val TALL_CARD_HEIGHT_DP = 200

/**
 * Displays the list of detail cards for the given TV show.
 * @param tvShow The TV show to display details for.
 */
@Composable
private fun DetailsCards(
    modifier: Modifier = Modifier,
    tvShow: TVShow,
    state: DetailsScreenState
) {
    val cardModifier = Modifier.fillMaxSize()
    Column(
        modifier = modifier.verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Spacer(modifier = Modifier.padding(vertical = 4.dp))

        CardRow(
            modifier = Modifier.height(SHORT_CARD_HEIGHT_DP.dp),
            cards = listOf(
                {
                    AIOverviewDetailCard(
                        modifier = cardModifier,
                        title = stringResource(R.string.ai_overview),
                        aiOverviewState = state.aiOverviewState
                    )
                }
            )
        )

        CardRow(
            modifier = Modifier.height(TALL_CARD_HEIGHT_DP.dp),
            cards = listOf(
                {
                    TitleAndDescriptionDetailCard(
                        modifier = cardModifier,
                        title = stringResource(R.string.overview),
                        description = tvShow.overview ?: stringResource(R.string.not_available)
                    )
                }
            )
        )

        CardRow(
            modifier = Modifier.height(SHORT_CARD_HEIGHT_DP.dp),
            cards = listOf(
                {
                    TitleAndDescriptionDetailCard(
                        modifier = cardModifier,
                        title = stringResource(R.string.origin_country_ies),
                        description = tvShow.originCountry?.joinToString(separator = "\n") { countryCode ->
                            countryCode.toLocalizedCountryNameWithEmoji()
                        } ?: stringResource(R.string.not_available)
                    )
                },
                {
                    TitleAndDescriptionDetailCard(
                        modifier = cardModifier,
                        title = stringResource(R.string.origin_language),
                        description = tvShow.originalLanguage?.toLocalizedLanguageName()
                            ?: stringResource(R.string.not_available)
                    )
                }
            )
        )

        CardRow(
            modifier = Modifier.height(SHORT_CARD_HEIGHT_DP.dp),
            cards = listOf(
                {
                    TitleAndDescriptionDetailCard(
                        modifier = cardModifier,
                        title = stringResource(R.string.first_air_date),
                        description = tvShow.firstAirDateObject?.let {
                            DateFormat.getDateInstance(DateFormat.LONG)
                                .format(it)
                        } ?: stringResource(R.string.not_available)
                    )
                },
                {
                    RatingDetailCard(
                        modifier = cardModifier,
                        tvShow = tvShow
                    )
                }
            )
        )
    }
}

/**
 * Displays the banner section of the details screen, containing the backdrop image and, title, and original title.
 * @param tvShow The TV show to display the banner for.
 * @param textShadow The shadow to add behind the text for readability.
 */
@Composable
private fun Banner(
    modifier: Modifier,
    tvShow: TVShow,
    textShadow: Shadow
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.BottomStart
    ) {
        BackdropImage(tvShow)
        BackgroundGradient()
        Column(
            modifier = Modifier.padding(BORDER_PADDING_DP.dp)
        ) {
            Title(tvShow, textShadow)
            OriginalTitle(tvShow, textShadow)
        }
    }
}

/**
 * Displays the backdrop image using [MyAsyncImage].
 * @param tvShow The TV show to display the backdrop image for.
 */
@Composable
private fun BackdropImage(tvShow: TVShow) {
    MyAsyncImage(
        model = tvShow.backdropUrl,
        contentDescription = stringResource(R.string.movie_backdrop),
        contentScale = ContentScale.Crop,
        modifier = Modifier.fillMaxWidth()
    )
}

/**
 * Vertical gradient used over the backdrop image to improve text readability.
 */
@Composable
private fun BackgroundGradient() {
    val surfaceColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.9f)
    val colorStops = arrayOf(
        0.0f to Color.Transparent,
        1.0f to surfaceColor
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colorStops = colorStops
                )
            )
    )
}

/**
 * Displays the title of the TV show.
 * @param tvShow The TV show to display the title for.
 * @param textShadow The shadow to add behind the text for readability.
 */
@Composable
private fun Title(
    tvShow: TVShow,
    textShadow: Shadow
) {
    Text(
        text = tvShow.name ?: stringResource(R.string.not_available),
        style = AppTypography.headlineLarge.copy(
            color = MaterialTheme.colorScheme.primary,
            shadow = textShadow
        ),
        maxLines = 3,
        overflow = TextOverflow.Ellipsis
    )
}

/**
 * Displays the original title of the TV show.
 * @param tvShow The TV show to display the original title for.
 * @param textShadow The shadow to add behind the text for readability.
 */
@Composable
private fun OriginalTitle(
    tvShow: TVShow,
    textShadow: Shadow
) {
    Text(
        text = tvShow.originalName ?: stringResource(R.string.not_available),
        style = AppTypography.titleMedium.copy(
            color = MaterialTheme.colorScheme.primary,
            fontStyle = FontStyle.Italic,
            shadow = textShadow
        ),
        maxLines = 2,
        overflow = TextOverflow.Ellipsis
    )
}

/**
 * A horizontal row to display multiple cards with even sizing and spacing.
 * @param modifier the [Modifier] to be applied to the row
 * ([androidx.compose.ui.Modifier.fillMaxSize] is applied over it).
 * @param spacing The amount of horizontal spacing in [Dp] between cards.
 * @param cards The list of composable functions ([Card]) to include in the row.
 */
@Composable
private fun CardRow(
    modifier: Modifier,
    spacing: Dp = 16.dp,
    cards: List<@Composable () -> Unit>
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(spacing)
    ) {
        cards.forEach { card ->
            // A weight of 1 distributes all cards evenly in the row
            Box(modifier = Modifier.weight(1f)) { card() }
        }
    }
}

@Preview(showBackground = true, heightDp = 1000, uiMode = Configuration.ORIENTATION_PORTRAIT)
@Preview(showBackground = true, device = "spec:width=300dp,height=1000dp")
@Preview(
    showBackground = true,
    uiMode = Configuration.ORIENTATION_LANDSCAPE,
    device = "spec:width=891dp,height=1000dp"
)
annotation class TVShowDetailsPreviewAnnotation

@Composable
@TVShowDetailsPreviewAnnotation
private fun TVShowDetailsPreview() {
    TVShowDetails(
        TVShow.examples[0],
        state = DetailsScreenState(aiOverviewState = AIOverviewState.Success()),
    )
}

@Composable
@TVShowDetailsPreviewAnnotation
private fun TVShowDetailsNullPreview() {
    TVShowDetails(
        TVShow.nullExample,
        state = DetailsScreenState(aiOverviewState = AIOverviewState.Success()),
    )
}