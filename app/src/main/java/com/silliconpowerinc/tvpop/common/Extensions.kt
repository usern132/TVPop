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
    val locale = Locale.Builder().setRegion(this).build()
    return locale.displayCountry
}


private const val FIRST_REGIONAL_INDICATOR_SYMBOL = 0x1F1E6

fun String.toFlagEmoji(): String {
    val countryCode = this

    // Return a default flag emoji if the country code is invalid (🏳️)
    if (countryCode.length != 2) return "\uD83C\uDFF3"

    return countryCode.uppercase(Locale.ROOT).map { char ->
        val offset = char - 'A'
        Character.toChars(offset + FIRST_REGIONAL_INDICATOR_SYMBOL).concatToString()
    }.joinToString("")
}