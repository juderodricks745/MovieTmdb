package com.davidbronn.movietmdb.domain.api

import com.davidbronn.movietmdb.data.model.PersonResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by Jude on 11/September/2020
 */
interface PersonApi {

    @GET(API_PATH)
    suspend fun fetchPersonDetails(@Path(PERSON_ID) movieId: Int): Response<PersonResponse>

    companion object Keys {
        const val PERSON_ID: String = "personId"
        const val API_PATH: String = "person/{personId}"
    }
}