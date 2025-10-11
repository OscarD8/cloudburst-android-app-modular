package com.example.data.di

import com.example.data.LocationRepositoryImpl
import com.example.domain.repository.LocationRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindLocationRepository(
        locationRepositoryImpl: LocationRepositoryImpl
    ) : LocationRepository
}
