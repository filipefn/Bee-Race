package com.beerace.di

import com.beerace.commons.navigation.NavGraph
import com.beerace.navigation.MainNavGraph
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
internal interface NavigationModule {

    @Binds
    fun providesMainNavGraph(mainNavGraph: MainNavGraph): NavGraph
}