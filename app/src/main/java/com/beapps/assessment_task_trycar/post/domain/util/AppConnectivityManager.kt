package com.beapps.assessment_task_trycar.post.domain.util

import kotlinx.coroutines.flow.Flow

interface AppConnectivityManager {
    fun getNetworkState () : Flow<NetworkState>
    fun isNetworkAvailable () : Boolean
    fun disconnect ()
}

enum class NetworkState {
        CONNECTED,
        LOST
}