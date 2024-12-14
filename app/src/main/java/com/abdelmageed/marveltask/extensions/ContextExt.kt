package com.abdelmageed.marveltask.extensions

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri

fun Context.openLinkUrl(link: String) {
    var url = link
    if (!url.startsWith("http://") && !url.startsWith("https://")) {
        url = "http://$url"
    }

    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    this.startActivity(browserIntent)
}

fun Context.isInternetAvailable(): Boolean {
    val connectivityManager =
        this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork = connectivityManager.activeNetwork ?: return false
    val networkCapabilities =
        connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
    return networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
}