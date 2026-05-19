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
import java.text.DateFormat

private const val BACKDROP_WEIGHT = 0.4f

@Composable
fun TVShowDetails(tvShow: TVShow) {
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
            tvShow = tvShow
        )
    }
}

private const val SHORT_CARD_HEIGHT_DP = 150
private const val TALL_CARD_HEIGHT_DP = 200

@Composable
private fun DetailsCards(modifier: Modifier = Modifier, tvShow: TVShow) {
    val cardModifier = Modifier.fillMaxSize()
    Column(
        modifier = modifier.verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Spacer(modifier = Modifier.padding(vertical = 4.dp))
        CardRow(
            modifier = Modifier.height(TALL_CARD_HEIGHT_DP.dp),
            cards = listOf(
                {
                    TitleAndDescriptionDetailCard(
                        modifier = cardModifier,
                        title = stringResource(R.string.overview),
                        description = tvShow.overview
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
                        description = tvShow.originCountry.joinToString(separator = "\n") { countryCode ->
                            countryCode.toLocalizedCountryNameWithEmoji()
                        }
                    )
                },
                {
                    TitleAndDescriptionDetailCard(
                        modifier = cardModifier,
                        title = stringResource(R.string.origin_language),
                        description = tvShow.originalLanguage.toLocalizedLanguageName()
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
                        description = DateFormat.getDateInstance(DateFormat.LONG)
                            .format(tvShow.firstAirDateObject)
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

@Composable
private fun BackdropImage(tvShow: TVShow) {
    MyAsyncImage(
        model = tvShow.backdropUrl,
        contentDescription = stringResource(R.string.movie_backdrop),
        contentScale = ContentScale.Crop,
        modifier = Modifier.fillMaxWidth()
    )
}

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

@Composable
private fun Title(
    tvShow: TVShow,
    textShadow: Shadow
) {
    Text(
        text = tvShow.name,
        style = AppTypography.headlineLarge.copy(
            color = MaterialTheme.colorScheme.primary,
            shadow = textShadow
        ),
        maxLines = 3,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
private fun OriginalTitle(
    tvShow: TVShow,
    textShadow: Shadow
) {
    Text(
        text = tvShow.originalName,
        style = AppTypography.titleMedium.copy(
            color = MaterialTheme.colorScheme.primary,
            fontStyle = FontStyle.Italic,
            shadow = textShadow
        ),
        maxLines = 2,
        overflow = TextOverflow.Ellipsis
    )
}

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

@Composable
@Preview(showBackground = true, heightDp = 1000, uiMode = Configuration.ORIENTATION_PORTRAIT)
@Preview(showBackground = true, device = "spec:width=300dp,height=1000dp")
@Preview(
    showBackground = true,
    uiMode = Configuration.ORIENTATION_LANDSCAPE,
    device = "spec:width=891dp,height=1000dp"
)
private fun TVShowDetailsPreview() {
    TVShowDetails(TVShow.examples[0])
}
