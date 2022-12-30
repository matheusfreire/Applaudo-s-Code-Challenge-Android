package com.msf.tvshows.extensions

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

object Network {
    fun isInternetAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkCapabilities = connectivityManager.activeNetwork ?: return false
        val network = connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
        return when {
            network.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || network.hasTransport(
                NetworkCapabilities.TRANSPORT_CELLULAR
            ) || network.hasTransport(
                NetworkCapabilities.TRANSPORT_ETHERNET
            ) -> true
            else -> false
        }
    }
}
