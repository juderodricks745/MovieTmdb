package com.davidbronn.movietmdb.di

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by Jude on 15/January/2022
 */
@InstallIn(SingletonComponent::class)
@Module
class GsonModule {

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return Gson()
    }
}