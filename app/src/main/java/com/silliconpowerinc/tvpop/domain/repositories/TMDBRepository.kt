package com.silliconpowerinc.tvpop.domain.models.repositories

import androidx.paging.PagingData
import com.silliconpowerinc.tvpop.domain.models.TVShow
import kotlinx.coroutines.flow.Flow

interface TMDBRepository {
    fun getTVShowsFlow(language: String = "en-US"): Flow<PagingData<TVShow>>
}