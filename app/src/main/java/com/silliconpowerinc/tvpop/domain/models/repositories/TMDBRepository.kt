package com.silliconpowerinc.tvpop.domain.models.repositories

import com.silliconpowerinc.tvpop.domain.models.TVShow

interface TMDBRepository {
    suspend fun getTVShows(language: String = "en-US", page: Int): List<TVShow>
}