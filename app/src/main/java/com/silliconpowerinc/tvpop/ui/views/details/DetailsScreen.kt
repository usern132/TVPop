package com.silliconpowerinc.tvpop.ui.views.details

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.silliconpowerinc.tvpop.R
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
    else Text(stringResource(R.string.error_occurred))
}

