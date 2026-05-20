package com.silliconpowerinc.tvpop.data.utils

import android.content.ComponentCallbacks
import android.content.Context
import android.content.res.Configuration
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * Interface for observing the current language of the device.
 */
interface LanguageObserver {
    /**
     * List of the locales supported by the app.
     */
    val supportedLocales: List<String>

    /**
     * Default locale to fall back to if the device's current locale is not supported.
     */
    val defaultLocale: String

    /**
     * A [StateFlow] that emits the current language tag (e.g., "en-US", "es-ES") whenever it changes.
     */
    val languageTagFlow: StateFlow<String>
}

/**
 * Implementation of [LanguageObserver] that listens to configuration changes to track language updates.
 *
 * @property context The application context used to register for component callbacks.
 */
class LanguageObserverImpl(private val context: Context) : LanguageObserver, ComponentCallbacks {
    override val supportedLocales: List<String>
        get() = listOf(
            "en-US",
            "es-ES",
            "ca-ES",
        )

    override val defaultLocale: String
        get() = "en-US"

    private val _languageTagFlow = MutableStateFlow(getCurrentLocale())
    override val languageTagFlow: StateFlow<String> = _languageTagFlow

    init {
        context.registerComponentCallbacks(this)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        val newLocale = newConfig.locales[0].toLanguageTag()
        val emitLocale = if (newLocale in supportedLocales) newLocale else defaultLocale
        _languageTagFlow.value = emitLocale
    }

    @Deprecated("Deprecated in Java")
    override fun onLowMemory() {}

    private fun getCurrentLocale(): String {
        val currentLocale = context.resources.configuration.locales[0].toLanguageTag()
        return if (currentLocale in supportedLocales) currentLocale else defaultLocale
    }
}
