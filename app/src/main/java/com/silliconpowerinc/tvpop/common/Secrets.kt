package com.silliconpowerinc.tvpop.common

import io.github.cdimascio.dotenv.dotenv

object Secrets {
    private val env = dotenv {
        directory = "/assets"
        filename = "env"
    }

    private fun getEnvVariable(key: String): String =
        env[key] ?: throw IllegalStateException("$key not in env file")

    val TMDB_API_KEY: String = getEnvVariable("TMDB_API_KEY")
}