package com.silliconpowerinc.tvpop.ui.views.list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage
import com.silliconpowerinc.tvpop.domain.models.TVShow
import com.silliconpowerinc.tvpop.ui.theme.Typography

@Composable
fun TVShowListItem(
    modifier: Modifier = Modifier,
    tvShow: TVShow
) {
    Box(
        modifier = modifier
            .height(100.dp)
    ) {
        BackgroundImage(tvShow)
        BackgroundGradient()

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.Center
            ) {
                val separator = "•"
                val details = listOf(
                    tvShow.originCountry,
                    tvShow.firstAirDate
                )
                Text(
                    text = tvShow.name,
                    style = Typography.titleLarge.copy(
                        // The shadow keeps the text legible when the background image is the same color as the text.
                        shadow = Shadow(
                            color = MaterialTheme.colorScheme.surface,
                            offset = Offset(4.0f, 4.0f),
                            blurRadius = 24f
                        )
                    )
                )
                Text(
                    text = details.joinToString(separator = " $separator "),
                    style = Typography.bodyMedium
                )
            }
        }
    }
}

@Composable
private fun BackgroundImage(tvShow: TVShow) {
    SubcomposeAsyncImage(
        model = tvShow.backdropUrl,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
        loading = {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(48.dp)
                )
            }
        },
        error = {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Error, contentDescription = null)
            }
        },
        contentDescription = null,
    )
}

@Composable
private fun BackgroundGradient() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.surface,
                        Color.Transparent
                    )
                )
            )
    )
}


@Composable
@Preview(showBackground = true)
private fun TVShowListItemPreview() {
    TVShowListItem(tvShow = TVShow.examples[0])
}