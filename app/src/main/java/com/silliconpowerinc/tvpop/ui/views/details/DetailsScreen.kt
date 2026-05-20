package com.silliconpowerinc.tvpop.ui.views.details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.silliconpowerinc.tvpop.R
import com.silliconpowerinc.tvpop.ui.common.BORDER_PADDING_DP
import com.silliconpowerinc.tvpop.ui.viewmodels.TVShowsViewModel
import com.silliconpowerinc.tvpop.ui.views.details.components.TVShowDetails
import org.koin.compose.viewmodel.koinViewModel

/**
 * Screen that displays the details of a specific TV show.
 *
 * @param tvShowsViewModel The ViewModel used to retrieve the TV show data.
 * @param tvShowId The unique identifier of the TV show to display.
 */
@Composable
fun DetailsScreen(
    tvShowsViewModel: TVShowsViewModel = koinViewModel(),
    tvShowId: Int
) {
    val tvShow = tvShowsViewModel.getTVShow(id = tvShowId)
    val state by tvShowsViewModel.uiState.collectAsStateWithLifecycle()
    val language by tvShowsViewModel.languageTagFlow.collectAsStateWithLifecycle()

    if (tvShow != null) {
        LaunchedEffect(tvShowId, language) {
            tvShowsViewModel.loadAIOverview(tvShowId = tvShowId)
        }
        TVShowDetails(
            tvShow = tvShow,
            state = state
        )
    } else ErrorScreen()
}

/**
 * Error screen displayed when the TV show details cannot be loaded.
 */
@Composable
private fun ErrorScreen() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(BORDER_PADDING_DP.dp)
    ) {
        Text(stringResource(R.string.error_occurred))
    }
}
