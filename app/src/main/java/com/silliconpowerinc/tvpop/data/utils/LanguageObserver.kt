package com.silliconpowerinc.tvpop.data.utils

import android.content.ComponentCallbacks
import android.content.Context
import android.content.res.Configuration
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

interface LanguageObserver {
    val languageTagFlow: Flow<String>
}

class LanguageObserverImpl(private val context: Context) : LanguageObserver, ComponentCallbacks {
    private val _languageTagFlow = MutableStateFlow(getCurrentLanguage())
    override val languageTagFlow: Flow<String> = _languageTagFlow

    init {
        context.registerComponentCallbacks(this)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        _languageTagFlow.value = newConfig.locales[0].toLanguageTag()
    }

    @Deprecated("Deprecated in Java")
    override fun onLowMemory() {}

    private fun getCurrentLanguage() = context.resources.configuration.locales[0].toLanguageTag()
}