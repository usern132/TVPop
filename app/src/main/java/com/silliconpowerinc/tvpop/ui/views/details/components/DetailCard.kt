package com.silliconpowerinc.tvpop.ui.views.details.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.silliconpowerinc.tvpop.R
import com.silliconpowerinc.tvpop.domain.models.TVShow
import com.silliconpowerinc.tvpop.ui.theme.AppTypography

/**
 * A generic card used to display a specific detail with a title.
 *
 * @param modifier The modifier to be applied to the [Card].
 * @param title The title of the detail.
 * @param content The composable content of the detail.
 */
@Composable
private fun DetailCard(
    modifier: Modifier = Modifier,
    title: String,
    content: @Composable () -> Unit
) {
    Card(modifier = modifier) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                style = AppTypography.titleLarge,
                color = MaterialTheme.colorScheme.secondary
            )
            content()
        }
    }
}

/**
 * A [DetailCard] that displays a title and a description string.
 *
 * @param modifier The modifier to be applied to the [Card].
 * @param title The title of the detail.
 * @param description The description text to display.
 */
@Composable
fun TitleAndDescriptionDetailCard(
    modifier: Modifier = Modifier,
    title: String,
    description: String
) {
    DetailCard(
        modifier = modifier,
        title = title
    ) {
        Text(
            // Allow the text inside the card to be scrolled if it doesn't fit
            modifier = Modifier.verticalScroll(rememberScrollState()),
            text = description.ifEmpty { stringResource(R.string.not_available) },
        )
    }
}

/**
 * A [DetailCard] that displays the rating and vote count of a TV show.
 *
 * @param modifier The modifier to be applied to the [Card].
 * @param tvShow The TV show whose rating is to be displayed.
 */
@Composable
fun RatingDetailCard(
    modifier: Modifier,
    tvShow: TVShow
) {
    DetailCard(
        modifier = modifier,
        title = stringResource(R.string.rating)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(
                space = 8.dp,
                alignment = Alignment.CenterVertically
            )
        ) {
            RatingAndVoteCount(tvShow)
            RatingBar(tvShow)
        }
    }
}

/**
 * Displays a linear progress bar with a color palette representing the TV show's rating.
 */
@Composable
private fun RatingBar(tvShow: TVShow) {
    val voteAverage = tvShow.voteAverage ?: return
    LinearProgressIndicator(
        modifier = Modifier.height(10.dp),
        progress = { (voteAverage / 10).toFloat() },
        color = when (voteAverage.toInt()) {
            0, 1 -> Color(0xFFB71C1C) // Dark Red
            2 -> Color(0xFFD32F2F)    // Red
            3 -> Color(0xFFE64A19)    // Deep Orange
            4 -> Color(0xFFF57C00)    // Orange
            5 -> Color(0xFFFFA000)    // Amber
            6 -> Color(0xFFFBC02D)    // Yellow
            7 -> Color(0xFFAFB42B)    // Lime
            8 -> Color(0xFF689F38)    // Light Green
            9 -> Color(0xFF388E3C)    // Green
            10 -> Color(0xFF1B5E20)   // Dark Green
            else -> MaterialTheme.colorScheme.primary
        },
        trackColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f),
        gapSize = 0.dp,
        drawStopIndicator = {}
    )
}

/**
 * Displays a star icon, the numerical rating and the number of votes.
 */
@Composable
private fun RatingAndVoteCount(tvShow: TVShow) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Star,
            contentDescription = null,
            modifier = Modifier.size(40.dp),
            tint = MaterialTheme.colorScheme.tertiary
        )
        Text(
            text = tvShow.voteAverage?.let { "%.2f".format(it) } ?: stringResource(R.string.n_a),
            style = AppTypography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
    }
    Text(
        text = tvShow.voteCount?.let { stringResource(R.string.votes, it) }
            ?: stringResource(R.string.n_a)
    )
}

@Preview(showBackground = true)
@Composable
fun RatingDetailCardPreview(
    sizeDp: Dp = 200.dp,
    tvShow: TVShow = TVShow.examples[0]
) {
    Box(modifier = Modifier.size(sizeDp)) {
        RatingDetailCard(
            modifier = Modifier.padding(16.dp),
            tvShow = tvShow
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RatingDetailCardPreviewLarge() {
    RatingDetailCardPreview(sizeDp = 350.dp)
}

@Preview(showBackground = true)
@Composable
fun RatingDetailCardPreviewSmall() {
    RatingDetailCardPreview(sizeDp = 170.dp)
}

@Preview(showBackground = true)
@Composable
fun RatingDetailCardNullPreview(sizeDp: Dp = 200.dp) {
    RatingDetailCardPreview(tvShow = TVShow.nullExample)
}
