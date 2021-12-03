package com.codingwithmitch.food2forkcompose.presentation.util

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LifecycleOwner
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ConnectivityManager
@Inject constructor(
    application: Application
) {
    private val connectionLiveData = ConnectionLiveData(application)

    var isNetworkAvailable by mutableStateOf(false)
        private set

    fun registerConnectionObserver(lifecycleOwner: LifecycleOwner) {
        connectionLiveData.observe(lifecycleOwner) { isConnected ->
            if (isConnected != null) {
                isNetworkAvailable = isConnected
            }
        }
    }

    fun unregisterConnectionObserver(lifecycleOwner: LifecycleOwner) {
        connectionLiveData.removeObservers(lifecycleOwner)
    }
}