package com.davidbronn.movietmdb.domain.repository

import com.davidbronn.movietmdb.domain.model.CastItemModel
import com.davidbronn.movietmdb.domain.model.DetailsModel
import com.davidbronn.movietmdb.utils.misc.Resource
import kotlinx.coroutines.flow.Flow

/**
 * Created by Jude on 12/January/2020
 */
interface DetailsRepository {
    fun fetchMovieDetails(movieId: Int): Flow<Resource<DetailsModel>>
    fun fetchMoviesCast(movieId: Int): Flow<Resource<List<CastItemModel>>>
    fun fetchSimilarMovies(movieId: Int): Flow<Resource<List<CastItemModel>>>
}