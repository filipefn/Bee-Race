package com.beerace.network.interceptor

import com.beerace.network.util.ConnectionManager
import okhttp3.Interceptor
import javax.inject.Inject
import okhttp3.Response as OkHttpResponse

internal class NetworkStatusInterceptor @Inject constructor(
    private val connectionManager: ConnectionManager
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): OkHttpResponse =
        if (connectionManager.isConnected) {
            chain.proceed(chain.request())
        } else {
            throw Exception()
        }
}