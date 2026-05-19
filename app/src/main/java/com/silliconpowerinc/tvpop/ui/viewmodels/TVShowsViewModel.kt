package com.silliconpowerinc.tvpop.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.filter
import com.silliconpowerinc.tvpop.data.utils.LanguageObserver
import com.silliconpowerinc.tvpop.domain.models.TVShow
import com.silliconpowerinc.tvpop.domain.repositories.TMDBRepository
import com.silliconpowerinc.tvpop.ui.views.list.components.TVShowListEvent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.KoinViewModel

@KoinViewModel
class TVShowsViewModel(
    private val tmdbRepository: TMDBRepository,
    languageObserver: LanguageObserver
) : ViewModel() {
    fun onEvent(event: TVShowListEvent) {}

    private val languageTagFlow = languageObserver.languageTagFlow

    @OptIn(ExperimentalCoroutinesApi::class)
    val tvShowsFlow = languageTagFlow.flatMapLatest { language ->
        tmdbRepository.getTVShowsFlow(language = language)
            .map { pagingData ->
                val receivedIds = mutableSetOf<Int>()
                // Apply a filter to remove duplicate entries returned by the API
                // (I've found duplicates across pages 1 and 2 with IDs 4604 and 66732)
                pagingData.filter { tvShow ->
                    val isNew = tvShow.id !in receivedIds
                    // Add new IDs to the set. If the ID has already been seen, it gets ignored.
                    if (isNew) receivedIds.add(tvShow.id)
                    // If isNew, include it in the flow (true); else, filter it out (false).
                    return@filter isNew
                }
            }
    }.cachedIn(viewModelScope)

    fun getTVShow(id: Int): TVShow? = tmdbRepository.getTVShow(id)
}