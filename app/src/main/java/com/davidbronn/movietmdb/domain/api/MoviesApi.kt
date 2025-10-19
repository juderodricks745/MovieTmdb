package com.davidbronn.movietmdb.domain.api

import com.davidbronn.movietmdb.data.model.MovieItemResponse

/**
 * Created by Jude on 04/January/2020
 */
interface MoviesApi {

    suspend fun fetchPopularMoviesAsync(pageNumber: Int): MovieItemResponse
}