package com.davidbronn.movietmdb.data.remote

import com.davidbronn.movietmdb.data.model.PersonResponse
import com.davidbronn.movietmdb.domain.api.PersonApi
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import javax.inject.Inject

class PersonApiImpl @Inject constructor(
    private val httpClient: HttpClient,
    private val baseUrl: String
) : PersonApi {

    override suspend fun fetchPersonDetails(personId: Int): PersonResponse {
        return httpClient.get("${baseUrl}person/$personId").body()
    }
}