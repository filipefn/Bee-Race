package com.beerace.network.di

import com.beerace.network.interceptor.NetworkStatusInterceptor
import com.beerace.network.util.ConnectivityObserver
import com.beerace.network.util.NetworkConnectivityObserver
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface InterceptorModule {

    companion object {

        @Singleton
        @Provides
        fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor =
            HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
    }

    @Singleton
    @Binds
    fun provideNetworkStatusInterceptor(networkStatusInterceptor: NetworkStatusInterceptor): Interceptor

    @Binds
    fun provideConnectivityObserver(networkConnectivityObserver: NetworkConnectivityObserver): ConnectivityObserver
}