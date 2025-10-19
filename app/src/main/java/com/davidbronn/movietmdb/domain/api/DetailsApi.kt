package com.davidbronn.movietmdb.domain.api

import com.davidbronn.movietmdb.data.model.DetailsResponse
import com.davidbronn.movietmdb.data.model.MovieCredit
import com.davidbronn.movietmdb.data.model.MovieItemResponse

/**
 * Created by Jude on 12/January/2020
 */
interface DetailsApi {

    suspend fun fetchMovieDetailsAsync(movieId: Int): DetailsResponse

    suspend fun fetchSimilarMoviesAsync(movieId: Int): MovieItemResponse

    suspend fun fetchMoviesCreditAsync(movieId: Int): MovieCredit
}