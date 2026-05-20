package com.silliconpowerinc.tvpop.data.utils

import android.content.ComponentCallbacks
import android.content.Context
import android.content.res.Configuration
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * Interface for observing the current language of the device.
 */
interface LanguageObserver {
    /**
     * A [Flow] that emits the current language tag (e.g., "en-US", "es-ES") whenever it changes.
     */
    val languageTagFlow: Flow<String>
}

/**
 * Implementation of [LanguageObserver] that listens to configuration changes to track language updates.
 *
 * @property context The application context used to register for component callbacks.
 */
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
