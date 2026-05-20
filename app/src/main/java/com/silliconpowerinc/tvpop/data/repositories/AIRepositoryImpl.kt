package com.silliconpowerinc.tvpop.data.repositories

import com.silliconpowerinc.tvpop.data.sources.AISource
import com.silliconpowerinc.tvpop.domain.models.TVShow
import com.silliconpowerinc.tvpop.domain.repositories.AIRepository
import org.koin.core.annotation.Singleton

@Singleton
class AIRepositoryImpl(
    private val aiSource: AISource
) : AIRepository {
    override suspend fun generateAIOverview(tvShow: TVShow, language: String): String {
        return aiSource.generateAIOverview(tvShow, language)
    }
}