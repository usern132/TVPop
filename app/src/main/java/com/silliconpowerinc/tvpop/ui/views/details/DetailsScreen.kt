package com.silliconpowerinc.tvpop.ui.views.details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.silliconpowerinc.tvpop.R
import com.silliconpowerinc.tvpop.ui.common.BORDER_PADDING_DP
import com.silliconpowerinc.tvpop.ui.viewmodels.TVShowsViewModel
import com.silliconpowerinc.tvpop.ui.views.details.components.TVShowDetails
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun DetailsScreen(
    tvShowsViewModel: TVShowsViewModel = koinViewModel(),
    tvShowId: Int
) {
    val tvShow = tvShowsViewModel.getTVShow(id = tvShowId)
    if (tvShow != null) TVShowDetails(tvShow = tvShow)
    else ErrorScreen()
}

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
