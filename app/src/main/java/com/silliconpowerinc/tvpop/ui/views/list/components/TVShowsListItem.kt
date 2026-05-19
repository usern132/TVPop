package com.silliconpowerinc.tvpop.ui.views.list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
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
import androidx.compose.ui.unit.dp
import com.silliconpowerinc.tvpop.R
import com.silliconpowerinc.tvpop.common.toLocalizedCountryNameWithEmoji
import com.silliconpowerinc.tvpop.domain.models.TVShow
import com.silliconpowerinc.tvpop.ui.common.components.MyAsyncImage
import com.silliconpowerinc.tvpop.ui.theme.AppTypography
import com.silliconpowerinc.tvpop.ui.theme.textShadow
import java.text.DateFormat

sealed class TVShowListItemEvent : TVShowListEvent() {
    data class Click(val id: Int) : TVShowListItemEvent()
}

private const val LEFT_WEIGHT = 0.85f

@Composable
fun TVShowsListItem(
    modifier: Modifier = Modifier,
    tvShow: TVShow,
    onEvent: (event: TVShowListItemEvent) -> Unit
) {
    val textShadow = MaterialTheme.textShadow
    Box(
        modifier = modifier
            .height(100.dp)
            .clickable(enabled = true, onClick = { onEvent(TVShowListItemEvent.Click(tvShow.id)) })
    ) {
        BackgroundImage(tvShow)
        BackgroundGradient()

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.weight(LEFT_WEIGHT)
            ) {
                Title(tvShow, textShadow)
                Subtitle(tvShow, textShadow)
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.weight(1 - LEFT_WEIGHT)
            ) {
                RatingStarRow(tvShow, textShadow)
                RatingCount(tvShow, textShadow)
            }
        }
    }
}

@Composable
private fun RatingCount(
    tvShow: TVShow,
    textShadow: Shadow
) {
    Text(
        text = "(${tvShow.voteCount})",
        style = AppTypography.bodySmall.copy(
            fontStyle = FontStyle.Italic,
            shadow = textShadow
        ),
    )
}

@Composable
private fun RatingStarRow(
    tvShow: TVShow,
    textShadow: Shadow
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Star,
            contentDescription = null,
            modifier = Modifier.size(20.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        Text(
            text = "%.2f".format(tvShow.voteAverage),
            style = AppTypography.bodyMedium.copy(
                shadow = textShadow
            ),
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
private fun Subtitle(tvShow: TVShow, textShadow: Shadow) {
    val separator = "•"
    val details = listOf(
        tvShow.originCountry[0].toLocalizedCountryNameWithEmoji(),
        DateFormat.getDateInstance(DateFormat.SHORT)
            .format(tvShow.firstAirDateObject)
    )
    Text(
        text = details.joinToString(separator = " $separator "),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        style = AppTypography.bodyMedium.copy(
            shadow = textShadow
        ),
    )
}

@Composable
private fun Title(tvShow: TVShow, textShadow: Shadow) {
    Text(
        text = tvShow.name,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis,
        style = AppTypography.titleLarge.copy(
            // The shadow keeps the text legible when the background image is the same color as the text.
            shadow = textShadow
        ),
        color = MaterialTheme.colorScheme.primary
    )
}

@Composable
private fun BackgroundImage(tvShow: TVShow) {
    MyAsyncImage(
        model = tvShow.backdropUrl,
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
        contentScale = ContentScale.Crop,
        contentDescription = stringResource(R.string.movie_backdrop),
    )
}

@Composable
private fun BackgroundGradient() {
    val surfaceColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.9f)
    val colorStops = arrayOf(
        0.0f to surfaceColor,
        LEFT_WEIGHT - 0.2f to Color.Transparent,
        1.0f to surfaceColor,
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.horizontalGradient(
                    colorStops = colorStops
                )
            )
    )
}


@Preview(showBackground = true, backgroundColor = android.graphics.Color.WHITE.toLong())
@Preview(showBackground = true, backgroundColor = android.graphics.Color.CYAN.toLong())
@Preview(showBackground = true, backgroundColor = android.graphics.Color.MAGENTA.toLong())
@Preview(showBackground = true, backgroundColor = android.graphics.Color.DKGRAY.toLong())
@Preview(showBackground = true, backgroundColor = android.graphics.Color.BLACK.toLong())
annotation class TVShowListItemBackgroundColorAnnotations

@Composable
@TVShowListItemBackgroundColorAnnotations
private fun TVShowsListItemPreview() {
    TVShowsListItem(tvShow = TVShow.examples[0], onEvent = {})
}

@Composable
@TVShowListItemBackgroundColorAnnotations
private fun TVShowsListItemLongPreview() {
    TVShowsListItem(
        tvShow = TVShow.examples[0].copy(name = "This is a very long TV show name for testing purposes - very long!"),
        onEvent = {}
    )
}