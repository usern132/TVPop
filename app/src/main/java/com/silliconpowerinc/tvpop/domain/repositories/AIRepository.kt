package com.silliconpowerinc.tvpop.domain.repositories

import com.silliconpowerinc.tvpop.domain.models.TVShow

interface AIRepository {
    suspend fun generateAIOverview(tvShow: TVShow, language: String): String
}