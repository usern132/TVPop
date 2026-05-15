package com.silliconpowerinc.tvpop.ui.views.list

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.silliconpowerinc.tvpop.ui.viewmodels.TVShowsViewModel
import com.silliconpowerinc.tvpop.ui.views.list.components.TVShowList
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ListScreen(
    tvShowsViewModel: TVShowsViewModel = koinViewModel()
) {
    val tvShowsFlow = tvShowsViewModel.tvShowsFlow
    TVShowList(tvShowsFlow = tvShowsFlow)
}

@Composable
@Preview(showBackground = true)
private fun ListScreenPreview() {
    ListScreen()
}