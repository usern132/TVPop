package com.silliconpowerinc.tvpop.ui.views.details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.silliconpowerinc.tvpop.R
import com.silliconpowerinc.tvpop.common.toLocalizedCountryNameWithEmoji
import com.silliconpowerinc.tvpop.domain.models.TVShow
import com.silliconpowerinc.tvpop.ui.common.MyAsyncImage
import com.silliconpowerinc.tvpop.ui.theme.AppTypography
import com.silliconpowerinc.tvpop.ui.theme.textShadow

private const val BACKDROP_WEIGHT = 0.4f

private const val BORDER_PADDING_DP = 16

@Composable
fun TVShowDetails(
    tvShow: TVShow
) {
    val textShadow = MaterialTheme.textShadow
    Column() {
        Banner(tvShow, textShadow)
        Box(
            modifier = Modifier
                .weight(1 - BACKDROP_WEIGHT)
                .padding(horizontal = BORDER_PADDING_DP.dp, vertical = 24.dp)
        ) {
            Column() {
                Row() {
                    val cardModifier = Modifier
                        .weight(0.5f)
                        .height(150.dp)
                    Card(modifier = cardModifier) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(
                                text = "Origin country/ies",
                                fontWeight = FontWeight.Bold,
                                style = AppTypography.titleLarge
                            )
                            Text(text = "ES".toLocalizedCountryNameWithEmoji())

                        }
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Card(modifier = cardModifier) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(
                                text = "Origin country/ies",
                                fontWeight = FontWeight.Bold,
                                style = AppTypography.titleLarge
                            )
                            Text(text = "ES".toLocalizedCountryNameWithEmoji())

                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ColumnScope.Banner(
    tvShow: TVShow,
    textShadow: Shadow
) {
    Box(
        modifier = Modifier
            .weight(BACKDROP_WEIGHT)
            .fillMaxWidth(),
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
@Preview(showBackground = true)
private fun TVShowDetailsPreview() {
    TVShowDetails(TVShow.examples[0])
}
