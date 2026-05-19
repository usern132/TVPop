package com.silliconpowerinc.tvpop.di

import android.content.Context
import androidx.room.Room
import com.silliconpowerinc.tvpop.data.sources.TVShowsLocalSource
import com.silliconpowerinc.tvpop.data.utils.ConnectivityObserver
import com.silliconpowerinc.tvpop.data.utils.ConnectivityObserverImpl
import com.silliconpowerinc.tvpop.data.utils.LanguageObserver
import com.silliconpowerinc.tvpop.data.utils.LanguageObserverImpl
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Configuration
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
@ComponentScan("com.silliconpowerinc.tvpop")
@Configuration
class AppModule {
    @Single
    fun provideDatabase(context: Context): TVShowsLocalSource {
        return Room.databaseBuilder(
            context,
            TVShowsLocalSource::class.java,
            "tv_shows_database"
        ).build()
    }

    @Single
    fun provideConnectivityObserver(context: Context): ConnectivityObserver =
        ConnectivityObserverImpl(context)

    @Single
    fun provideLanguageObserver(context: Context): LanguageObserver =
        LanguageObserverImpl(context)
}