package com.abdelmageed.marveltask.data.di

import com.abdelmageed.marveltask.data.remote.apiCall.MarvelCharactersApiCall
import com.abdelmageed.marveltask.data.repository.DetailsRepositoryImpl
import com.abdelmageed.marveltask.data.repository.HomeRepositoryImpl
import com.abdelmageed.marveltask.data.repository.SearchRepositoryImpl
import com.abdelmageed.marveltask.domain.DetailsRepository
import com.abdelmageed.marveltask.domain.HomeRepository
import com.abdelmageed.marveltask.domain.SearchRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    init {
        System.loadLibrary("native-lib")
    }

    private external fun getPrivateKey(): String
    private external fun getPublicKey(): String

    @Provides
    fun provideMarvelApiClient(client: HttpClient): MarvelCharactersApiCall {
        return MarvelCharactersApiCall(client, getPrivateKey(), getPublicKey())
    }


    @Singleton
    @Provides
    fun provideHomeRepo(apiCall: MarvelCharactersApiCall): HomeRepository =
        HomeRepositoryImpl(apiCall)

    @Singleton
    @Provides
    fun provideDetailsRepo(httpClient: HttpClient): DetailsRepository =
        DetailsRepositoryImpl(httpClient, getPrivateKey(), getPublicKey())

    @Singleton
    @Provides
    fun provideSearchRepo(httpClient: HttpClient): SearchRepository =
        SearchRepositoryImpl(httpClient, getPrivateKey(), getPublicKey())
}