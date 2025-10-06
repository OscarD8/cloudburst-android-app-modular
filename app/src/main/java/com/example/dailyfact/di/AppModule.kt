package com.example.dailyfact.di

import com.example.data.FactRepositoryImpl
import com.example.domain.FactRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

//@Module
//@InstallIn(SingletonComponent::class)
//object AppModule {
//    @Provides
//    @Singleton
//    fun provideFactRepository(): FactRepository = FactRepositoryImpl()
//    @Provides
//    fun provideGetFactUseCase(factRepository: FactRepository) = GetRandomFactUseCase(factRepository)
//}

@Module // Place where you provide dependencies
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindFactRepository(
        factRepositoryImpl: FactRepositoryImpl
    ) : FactRepository
}

