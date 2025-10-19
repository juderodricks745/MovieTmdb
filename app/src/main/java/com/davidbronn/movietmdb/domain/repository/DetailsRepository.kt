package com.davidbronn.movietmdb.domain.repository

import com.davidbronn.movietmdb.domain.model.CastItemModel
import com.davidbronn.movietmdb.domain.model.DetailsModel
import com.davidbronn.movietmdb.utils.misc.Resource

/**
 * Created by Jude on 12/January/2020
 */
interface DetailsRepository {
    suspend fun fetchMovieDetails(movieId: Int): Resource<DetailsModel>
    suspend fun fetchMoviesCast(movieId: Int): Resource<List<CastItemModel>>
    suspend fun fetchSimilarMovies(movieId: Int): Resource<List<CastItemModel>>
}