package com.varalakshmiakella.weatherappjpmc.di

import com.varalakshmiakella.weatherappjpmc.locationSettings.DefaultLocationTracker
import com.varalakshmiakella.weatherappjpmc.locationSettings.LocationTracker
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@Module
@InstallIn(SingletonComponent::class)
abstract class LocationModule {

    @Binds
    @Singleton
    abstract fun bindLocationTracker(defaultLocationTracker: DefaultLocationTracker): LocationTracker
}