package com.davidbronn.movietmdb.di

import com.davidbronn.movietmdb.data.repository.DetailsRepositoryImpl
import com.davidbronn.movietmdb.data.repository.MoviesRepositoryImpl
import com.davidbronn.movietmdb.data.repository.PersonRepositoryImpl
import com.davidbronn.movietmdb.domain.repository.DetailsRepository
import com.davidbronn.movietmdb.domain.repository.MoviesRepository
import com.davidbronn.movietmdb.domain.repository.PersonRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

/**
 * Created by Jude on 28/June/2020
 */
@InstallIn(ViewModelComponent::class)
@Module
abstract class RepositoryModule {

    @Binds
    abstract fun provideMoviesRepository(repository: MoviesRepositoryImpl): MoviesRepository

    @Binds
    abstract fun provideDetailsRepository(repository: DetailsRepositoryImpl): DetailsRepository

    @Binds
    abstract fun providePersonRepository(repository: PersonRepositoryImpl): PersonRepository
}