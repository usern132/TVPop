package com.silliconpowerinc.tvpop.ui.views.details.components.detailcards

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.silliconpowerinc.tvpop.R
import com.silliconpowerinc.tvpop.domain.models.TVShow
import com.silliconpowerinc.tvpop.ui.views.details.AIOverviewState

/**
 * A [DetailCard] that displays an AI-generated overview of a TV show.
 *
 * @param modifier The modifier to be applied to the [androidx.compose.material3.Card].
 * @param title The title of the detail.
 * @param aiOverviewState The loading state of the AI overview, which determines what content to display.
 */
@Composable
fun AIOverviewDetailCard(
    modifier: Modifier = Modifier,
    title: String,
    aiOverviewState: AIOverviewState
) {
    val infiniteTransition = rememberInfiniteTransition(label = "infinite")

    val animatedValue by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 8000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "phase"
    )

    val defaultCardColors = CardDefaults.cardColors()

    DetailCard(
        modifier = modifier
            .clip(CardDefaults.shape)
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .drawBehind(animatedBackgroundGradient(animatedValue)),
        cardColors = defaultCardColors.copy(
            containerColor = defaultCardColors.containerColor.copy(
                alpha = 0.75f
            )
        ),
        title = title
    ) {
        when (aiOverviewState) {
            is AIOverviewState.Loading -> CircularProgressIndicator(modifier = Modifier)
            is AIOverviewState.Success -> Text(
                // Allow the text inside the card to be scrolled if it doesn't fit
                modifier = Modifier.verticalScroll(rememberScrollState()),
                text = aiOverviewState.overview
            )

            is AIOverviewState.Error ->
                if (aiOverviewState.message != null) {
                    Text(stringResource(R.string.error_occurred, aiOverviewState.message))
                } else {
                    Text(stringResource(R.string.unknown_error_occurred))
                }
        }
    }
}

@Composable
private fun animatedBackgroundGradient(animatedValue: Float): DrawScope.() -> Unit = {
    val animatedColor = Color.hsv(hue = animatedValue, saturation = 0.5f, value = 0.9f)

    val brush = Brush.verticalGradient(
        0.6f to Color.Transparent,
        1.0f to animatedColor,
        startY = 0f,
        endY = size.height
    )

    drawRect(brush)
}

@Preview(showBackground = true)
@Composable
private fun AIOverviewDetailCardPreview(
    sizeDp: Dp = 200.dp,
    tvShow: TVShow = TVShow.examples[0]
) {
    Box(modifier = Modifier.size(sizeDp)) {
        AIOverviewDetailCard(
            modifier = Modifier.padding(16.dp),
            title = stringResource(R.string.ai_overview),
            aiOverviewState = AIOverviewState.Success(tvShow.overview!!)
        )
    }
}