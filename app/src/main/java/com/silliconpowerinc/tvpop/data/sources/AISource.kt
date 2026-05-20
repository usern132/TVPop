package com.silliconpowerinc.tvpop.data.sources

import com.silliconpowerinc.tvpop.domain.models.TVShow
import io.github.vyfor.groqkt.GroqClient
import io.github.vyfor.groqkt.GroqModel
import org.koin.core.annotation.Singleton

@Singleton
class AISource(
    private val groqClient: GroqClient
) {
    suspend fun generateAIOverview(tvShow: TVShow, language: String): String {
        val response = groqClient.chat {
            model = GroqModel.LLAMA_3_1_8B_INSTANT
            messages {
                system("Your task is to provide a short and concise overview of a TV show. The overview must not exceed 4 sentences and 100 words. It should summarise all the attributes found in the show's information that are relevant to a user interested in learning about this TV show in an app that lists the most popular shows. You will be provided with a .toString() representation of the Kotlin data class that holds the information for this TV show. Use only this information to formulate your response. Keep a civil, friendly, and engaging tone. Avoid spoilers.")
                user("$tvShow")
            }
        }
        if (response.isFailure) throw response.exceptionOrNull() ?: Exception("")
        return response.getOrThrow().data.choices.first().message.content
            ?: Exception("No content in AI response").let { throw it }
    }
}