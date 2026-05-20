package com.silliconpowerinc.tvpop.data.repositories

import com.silliconpowerinc.tvpop.domain.models.TVShow
import com.silliconpowerinc.tvpop.domain.repositories.AIRepository
import org.koin.core.annotation.Singleton

@Singleton
class AIRepositoryImpl : AIRepository {
    override suspend fun generateAIOverview(tvShow: TVShow, language: String): String {
        return "example repo, tv show: ${tvShow.name}, language: $language"
    }
}