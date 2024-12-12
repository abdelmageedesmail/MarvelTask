package com.abdelmageed.marveltask.data.di

import com.abdelmageed.marveltask.data.repository.HomeRepositoryImpl
import com.abdelmageed.marveltask.domain.HomeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideHomeRepo(httpClient: HttpClient): HomeRepository = HomeRepositoryImpl(httpClient)

}