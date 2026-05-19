package com.silliconpowerinc.tvpop.data.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

interface ConnectivityObserver {
    val isNetworkAvailable: Boolean
}

class ConnectivityObserverImpl(private val context: Context) : ConnectivityObserver {
    /** Indicates whether the device has an active internet connection.
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