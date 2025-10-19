package com.davidbronn.movietmdb.data.remote

import com.davidbronn.movietmdb.data.model.DetailsResponse
import com.davidbronn.movietmdb.data.model.MovieCredit
import com.davidbronn.movietmdb.data.model.MovieItemResponse
import com.davidbronn.movietmdb.domain.api.DetailsApi
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import javax.inject.Inject

class DetailsApiImpl @Inject constructor(
    private val httpClient: HttpClient,
    private val baseUrl: String
) : DetailsApi {

    override suspend fun fetchMovieDetailsAsync(movieId: Int): DetailsResponse {
        return httpClient.get("${baseUrl}movie/$movieId").body()
    }

    override suspend fun fetchSimilarMoviesAsync(movieId: Int): MovieItemResponse {
        return httpClient.get("${baseUrl}movie/$movieId/similar").body()
    }

    override suspend fun fetchMoviesCreditAsync(movieId: Int): MovieCredit {
        return httpClient.get("${baseUrl}movie/$movieId/credits").body()
    }
}