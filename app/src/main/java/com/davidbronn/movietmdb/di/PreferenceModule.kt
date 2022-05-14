package com.davidbronn.movietmdb.di

import com.davidbronn.movietmdb.data.repository.PreferenceHelperImpl
import com.davidbronn.movietmdb.domain.repository.PreferenceHelper
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Created by Jude on 10/September/2021
 */
@InstallIn(SingletonComponent::class)
@Module
abstract class PreferenceModule {

    @Binds
    abstract fun providePreferenceRepository(repository: PreferenceHelperImpl): PreferenceHelper
}