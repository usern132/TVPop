package com.silliconpowerinc.tvpop.domain.repositories

import androidx.paging.PagingData
import com.silliconpowerinc.tvpop.domain.models.TVShow
import kotlinx.coroutines.flow.Flow

interface TMDBRepository {
    fun getTVShowsFlow(language: String): Flow<PagingData<TVShow>>
    fun getTVShow(id: Int): TVShow?
}