package com.davidbronn.movietmdb.di

import com.davidbronn.movietmdb.data.remote.DetailsApiImpl
import com.davidbronn.movietmdb.data.remote.MoviesApiImpl
import com.davidbronn.movietmdb.data.remote.PersonApiImpl
import com.davidbronn.movietmdb.domain.api.DetailsApi
import com.davidbronn.movietmdb.domain.api.MoviesApi
import com.davidbronn.movietmdb.domain.api.PersonApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*

@InstallIn(SingletonComponent::class)
@Module
class ApiModule {

    @Provides
    fun provideMovieApiService(httpClient: HttpClient, baseUrl: String): MoviesApi = 
        MoviesApiImpl(httpClient, baseUrl)

    @Provides
    fun provideDetailsApiService(httpClient: HttpClient, baseUrl: String): DetailsApi = 
        DetailsApiImpl(httpClient, baseUrl)

    @Provides
    fun providePersonApiService(httpClient: HttpClient, baseUrl: String): PersonApi = 
        PersonApiImpl(httpClient, baseUrl)
}