package com.silliconpowerinc.tvpop.ui.views.list

import androidx.compose.runtime.Composable
import com.silliconpowerinc.tvpop.ui.viewmodels.TVShowsViewModel
import com.silliconpowerinc.tvpop.ui.views.list.components.TVShowListEvent
import com.silliconpowerinc.tvpop.ui.views.list.components.TVShowListItemEvent
import com.silliconpowerinc.tvpop.ui.views.list.components.TVShowsList
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ListScreen(
    tvShowsViewModel: TVShowsViewModel = koinViewModel(),
    onNavigate: (TVShowListEvent) -> Unit
) {
    val tvShowsFlow = tvShowsViewModel.tvShowsFlow
    TVShowsList(
        tvShowsFlow = tvShowsFlow,
        onEvent = { event ->
            when (event) {
                is TVShowListItemEvent.Click -> onNavigate(event)
                else -> tvShowsViewModel.onEvent(event = event)
            }
        }
    )
}