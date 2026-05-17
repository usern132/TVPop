package com.silliconpowerinc.tvpop.ui.views.details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.silliconpowerinc.tvpop.R
import com.silliconpowerinc.tvpop.domain.models.TVShow
import com.silliconpowerinc.tvpop.ui.common.MyAsyncImage
import com.silliconpowerinc.tvpop.ui.theme.AppTypography
import com.silliconpowerinc.tvpop.ui.theme.textShadow

private const val BACKDROP_WEIGHT = 0.3f

private const val BORDER_PADDING_DP = 16

@Composable
fun TVShowDetails(
    tvShow: TVShow
) {
    val textShadow = MaterialTheme.textShadow
    Column {
        Box(
            modifier = Modifier.weight(BACKDROP_WEIGHT),
            contentAlignment = Alignment.BottomStart
        ) {
            MyAsyncImage(
                model = tvShow.backdropUrl,
                contentDescription = stringResource(R.string.movie_backdrop),
                contentScale = ContentScale.Crop
            )
            BackgroundGradient()
            Text(
                tvShow.name,
                style = AppTypography.headlineMedium.copy(
                    color = MaterialTheme.colorScheme.primary,
                    shadow = textShadow
                ),
                modifier = Modifier.padding(BORDER_PADDING_DP.dp)
            )
        }
        Box(
            modifier = Modifier.weight(1 - BACKDROP_WEIGHT)
        ) { }
    }
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
