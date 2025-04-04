package com.davidbronn.movietmdb.domain.api

import com.davidbronn.movietmdb.data.model.MovieItemResponse
import com.davidbronn.movietmdb.domain.api.MoviesApi.ApiKeys.KEY_MOVIE_PAGE
import com.davidbronn.movietmdb.domain.api.MoviesApi.ApiKeys.MOVIE_POPULAR
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Jude on 04/January/2020
 */
interface MoviesApi {

    @GET(MOVIE_POPULAR)
    suspend fun fetchPopularMoviesAsync(@Query(KEY_MOVIE_PAGE) pageNumber: Int):
            Response<MovieItemResponse>

    object ApiKeys {
        const val KEY_MOVIE_PAGE = "page"
        const val MOVIE_POPULAR = "movie/popular"
    }
}