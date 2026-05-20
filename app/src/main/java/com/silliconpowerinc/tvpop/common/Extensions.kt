package com.silliconpowerinc.tvpop.common

import java.util.Locale

/**
 * Converts a country code to its localized country name based on the device's current locale settings.
 *
 * @receiver The country code to be converted (e.g., "US", "FR").
 * @return The localized country name corresponding to the provided country code (e.g., "United States", "France").
 */
fun String.toLocalizedCountryName(): String {
    val countryCode = this
    val locale = Locale.Builder().setRegion(countryCode).build()
    return locale.displayCountry
}


private const val FIRST_REGIONAL_INDICATOR_SYMBOL = 0x1F1E6

/**
 * Converts a two-letter country code to its corresponding flag emoji.
 *
 * @receiver The ISO 3166-1 alpha-2 country code (e.g., "US", "ES").
 * @return The flag emoji associated with the country code, or a default flag if the code is invalid.
 */
fun String.toFlagEmoji(): String {
    val countryCode = this

    // Return a default flag emoji if the country code is invalid (🏳️)
    if (countryCode.length != 2) return "\uD83C\uDFF3"

    return countryCode.uppercase(Locale.ROOT).map { char ->
        val offset = char - 'A'
        Character.toChars(offset + FIRST_REGIONAL_INDICATOR_SYMBOL).concatToString()
    }.joinToString("")
}

/**
 * Converts a country code to a string with the flag emoji and the localized country name.
 *
 * @receiver The country code to be converted (e.g., "US", "FR").
 * @return A string with the flag emoji and the localized country name (e.g., "🇺🇸 United States").
 */
fun String.toLocalizedCountryNameWithEmoji(): String {
    val countryCode = this
    return countryCode.toFlagEmoji() + " " + countryCode.toLocalizedCountryName()
}

/**
 * Converts a language code to its localized language name based on the device's current locale settings.
 * @receiver The language code to be converted (e.g., "en", "es").
 * @return The localized language name corresponding to the provided language code (e.g., "English", "Spanish").
 */
fun String.toLocalizedLanguageName(): String {
    val languageCode = this
    val locale = Locale.Builder().setLanguage(languageCode).build()
    return locale.displayLanguage.replaceFirstChar { it.uppercase() }
}