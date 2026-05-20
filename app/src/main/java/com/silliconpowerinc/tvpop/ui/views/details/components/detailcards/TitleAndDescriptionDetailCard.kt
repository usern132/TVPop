package com.silliconpowerinc.tvpop.ui.views.details.components.detailcards

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.silliconpowerinc.tvpop.R
import com.silliconpowerinc.tvpop.domain.models.TVShow

/**
 * A [DetailCard] that displays a title and a description string.
 *
 * @param modifier The modifier to be applied to the [androidx.compose.material3.Card].
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

@Preview(showBackground = true)
@Composable
private fun TitleAndDescriptionDetailCardPreview(
    sizeDp: Dp = 200.dp,
    tvShow: TVShow = TVShow.examples[0]
) {
    Box(modifier = Modifier.size(sizeDp)) {
        TitleAndDescriptionDetailCard(
            modifier = Modifier.padding(16.dp),
            title = stringResource(R.string.overview),
            description = tvShow.overview ?: ""
        )
    }
}