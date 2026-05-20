package com.silliconpowerinc.tvpop.data.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

/**
 * Interface for observing the network connectivity status of the device.
 */
interface ConnectivityObserver {
    /**
     * Whether the device currently has an active internet connection.
     */
    val isNetworkAvailable: Boolean
}

/**
 * Implementation of [ConnectivityObserver] using the system's [ConnectivityManager].
 *
 * @property context The application context used to retrieve the connectivity service.
 */
class ConnectivityObserverImpl(private val context: Context) : ConnectivityObserver {
    /**
     * Indicates whether the device has an active internet connection.
     * Uses [NetworkCapabilities.NET_CAPABILITY_INTERNET] to determine availability.
     *
     * @return `true` if the device has an active internet connection, `false` otherwise.
     */
    override val isNetworkAvailable: Boolean
        get() {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val network = connectivityManager.activeNetwork ?: return false
            val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
            return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        }
}
