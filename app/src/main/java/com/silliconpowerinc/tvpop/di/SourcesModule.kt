package com.silliconpowerinc.tvpop.di

import com.silliconpowerinc.tvpop.data.sources.TMDBRemoteSource
import org.koin.dsl.bind
import org.koin.dsl.module
import org.koin.plugin.module.dsl.single

val sourcesModule = module {
    single<TMDBRemoteSource>() bind TMDBRemoteSource::class
}