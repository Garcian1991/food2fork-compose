package com.codingwithmitch.food2forkcompose.presentation.util

import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.util.Log
import androidx.lifecycle.LiveData
import com.codingwithmitch.food2forkcompose.interactors.app.DoesNetworkHaveInternet
import com.codingwithmitch.food2forkcompose.util.TAG
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ConnectionLiveData(context: Context): LiveData<Boolean>() {

    private var networkCallback: ConnectivityManager.NetworkCallback? = null
    private val cm = context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
    private val validNetworks: MutableSet<Network> = HashSet()

    override fun onActive() {
        super.onActive()
        networkCallback = createNetworkCallback()
        if (networkCallback != null) {
            val networkRequest = NetworkRequest.Builder()
                .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                .build()
            cm.registerNetworkCallback(networkRequest, networkCallback!!)
        }
    }

    override fun onInactive() {
        super.onInactive()
        if (networkCallback != null) {
            cm.unregisterNetworkCallback(networkCallback!!)
        }
        networkCallback = null
    }

    private fun createNetworkCallback() = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            Log.d(TAG, "onAvailable: $network")
            val networkCapabilities = cm.getNetworkCapabilities(network)
            val hasInternetCapability = networkCapabilities?.hasCapability(
                NetworkCapabilities.NET_CAPABILITY_INTERNET
            )
            if (hasInternetCapability == true) {
                CoroutineScope(Dispatchers.IO).launch {
                    val hasInternet = DoesNetworkHaveInternet.execute(network.socketFactory)
                    if (hasInternet) {
                        withContext(Dispatchers.Main) {
                            Log.d(TAG, "onAvailable: This network has internet $network")
                            validNetworks.add(network)
                            checkValidNetworks()
                        }
                    }
                }
            }
        }

        override fun onLost(network: Network) {
            super.onLost(network)
            Log.d(TAG, "onLost: $network")
            validNetworks.remove(network)
            checkValidNetworks()
        }
    }

    private fun checkValidNetworks() {
        postValue(validNetworks.isNotEmpty())
    }
}