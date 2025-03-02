package com.binit.zenwalls.data.repository

import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import com.binit.zenwalls.domain.model.NetworkStatus
import com.binit.zenwalls.domain.repository.NetworkConnectivityObserver
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.stateIn

class NetworkConnectivityObserverImpl(
    private val context: Context,
    private val scope: CoroutineScope

) : NetworkConnectivityObserver {

    private val _networkStatus: StateFlow<NetworkStatus> = observe().stateIn(
        scope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = NetworkStatus.DISCONNECTED
    )

    override val networkStatus: StateFlow<NetworkStatus> = _networkStatus

    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager


    private fun observe(): Flow<NetworkStatus> {
        return callbackFlow {
            val connectivityCallBack = object : NetworkCallback() {
                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    trySend(NetworkStatus.CONNECTED)
                }

                override fun onLost(network: Network) {
                    super.onLost(network)
                    trySend(NetworkStatus.DISCONNECTED)
                }

            }

            val request = NetworkRequest
                .Builder()
                .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
                .build()

            connectivityManager.registerNetworkCallback(request, connectivityCallBack)
            awaitClose {
                connectivityManager.unregisterNetworkCallback(connectivityCallBack)
            }
        }.distinctUntilChanged()
    }

}