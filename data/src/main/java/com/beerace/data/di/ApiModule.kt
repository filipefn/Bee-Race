package com.beerace.data.di

import com.beerace.data.api.ApiService
import com.beerace.data.repository.StatusRaceRepositoryImpl
import com.beerace.data.repository.TimeRaceRepositoryImpl
import com.beerace.domain.statusrace.StatusRaceRepository
import com.beerace.domain.timerace.TimeRaceRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class ApiModule {

    companion object {
        @Provides
        @Singleton
        fun provideRetrofit(
            retrofit: Retrofit
        ): ApiService = retrofit.create(ApiService::class.java)
    }

    @Binds
    abstract fun provideStatusRaceRepository(statusRaceRepositoryImpl: StatusRaceRepositoryImpl): StatusRaceRepository

    @Binds
    abstract fun provideTimeRaceRepository(timeRaceRepositoryImpl: TimeRaceRepositoryImpl): TimeRaceRepository
}