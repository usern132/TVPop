package com.silliconpowerinc.tvpop.ui.views.details.components.detailcards

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.silliconpowerinc.tvpop.R
import com.silliconpowerinc.tvpop.ui.views.details.AIOverviewState

@Composable
fun AIOverviewDetailCard(
    modifier: Modifier = Modifier.Companion,
    title: String,
    aiOverviewState: AIOverviewState
) {
    DetailCard(
        modifier = modifier,
        title = title
    ) {
        when (aiOverviewState) {
            is AIOverviewState.Loading -> CircularProgressIndicator(modifier = Modifier.Companion)
            is AIOverviewState.Success -> Text(aiOverviewState.overview)
            is AIOverviewState.Error ->
                if (aiOverviewState.message != null) {
                    Text(stringResource(R.string.error_occurred, aiOverviewState.message))
                } else {
                    Text(stringResource(R.string.unknown_error_occurred))
                }
        }
    }
}