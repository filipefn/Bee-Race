package com.beerace.network.di

import android.app.Application
import com.beerace.network.adapter.ApiCallAdapterFactory
import com.beerace.network.config.BeeRaceConstantsConfig
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal open class NetworkModule {

        private fun getBaseUrl() = "https://rtest.proxy.beeceptor.com"

        @Provides
        @Singleton
        fun provideGson(): Gson = GsonBuilder().create()

        @Provides
        @Singleton
        fun provideGsonConverterFactory(
            gson: Gson
        ): GsonConverterFactory = GsonConverterFactory.create(gson)


        @Provides
        @Singleton
        fun provideRetrofitBuilder(
            gsonConverterFactory: GsonConverterFactory,
        ): Retrofit.Builder = Retrofit.Builder()
            .baseUrl(getBaseUrl())
            .addConverterFactory(gsonConverterFactory)
            .addCallAdapterFactory(ApiCallAdapterFactory())

        @Singleton
        @Provides
        fun provideOkhttpClientBuilder(
            application: Application,
            loggingInterceptor: HttpLoggingInterceptor,
        ): OkHttpClient.Builder {
            val constants = BeeRaceConstantsConfig()
            val cache = Cache(application.cacheDir, constants.cacheSize)

            val builder = OkHttpClient.Builder()
            builder.addInterceptor(loggingInterceptor)

            val timeout = constants.timeout

            return builder
                .connectTimeout(timeout, TimeUnit.SECONDS)
                .readTimeout(timeout, TimeUnit.SECONDS)
                .writeTimeout(timeout, TimeUnit.SECONDS)
                .cache(cache)
        }

        @Singleton
        @Provides
        fun provideRetrofit(
            httpBuilder: OkHttpClient.Builder,
            retrofitBuilder: Retrofit.Builder
        ): Retrofit = retrofitBuilder
            .client(httpBuilder.build())
            .build()

}
