package com.binit.zenwalls.domain.repository

import com.binit.zenwalls.domain.model.NetworkStatus
import kotlinx.coroutines.flow.StateFlow

interface NetworkConnectivityObserver {
    val networkStatus: StateFlow<NetworkStatus>
}