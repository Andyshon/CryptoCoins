package com.andyshon.cryptocoins.utils.extensions

import android.content.Context
import android.net.ConnectivityManager

fun Context.isNetworkAvailable(): Boolean {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (connectivityManager.activeNetworkInfo == null)
        return false
    return connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo.isConnected
}