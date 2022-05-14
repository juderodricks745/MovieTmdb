package com.davidbronn.movietmdb.domain.repository

import androidx.paging.PagingData
import com.davidbronn.movietmdb.domain.model.ItemModel
import kotlinx.coroutines.flow.Flow

/**
 * Created by Jude on 03/October/2020
 */
interface MoviesRepository {
    fun fetchMovies(): Flow<PagingData<ItemModel>>
}