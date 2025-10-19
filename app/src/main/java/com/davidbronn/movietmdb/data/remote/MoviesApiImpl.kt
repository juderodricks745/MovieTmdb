package com.davidbronn.movietmdb.data.remote

import com.davidbronn.movietmdb.data.model.MovieItemResponse
import com.davidbronn.movietmdb.domain.api.MoviesApi
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import javax.inject.Inject

class MoviesApiImpl @Inject constructor(
    private val httpClient: HttpClient,
    private val baseUrl: String
) : MoviesApi {

    override suspend fun fetchPopularMoviesAsync(pageNumber: Int): MovieItemResponse {
        return httpClient.get("${baseUrl}movie/popular") {
            parameter("page", pageNumber)
        }.body()
    }
}