package com.davidbronn.movietmdb.di

import com.davidbronn.movietmdb.domain.api.DetailsApi
import com.davidbronn.movietmdb.domain.api.MoviesApi
import com.davidbronn.movietmdb.domain.api.PersonApi
import com.davidbronn.movietmdb.utils.extensions.api
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@InstallIn(SingletonComponent::class)
@Module
class ApiModule {

    @Provides
    fun provideMovieApiService(retrofit: Retrofit): MoviesApi = retrofit.api()

    @Provides
    fun provideDetailsApiService(retrofit: Retrofit): DetailsApi = retrofit.api()

    @Provides
    fun providePersonApiService(retrofit: Retrofit): PersonApi = retrofit.api()
}