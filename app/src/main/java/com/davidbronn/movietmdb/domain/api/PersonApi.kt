package com.davidbronn.movietmdb.domain.api

import com.davidbronn.movietmdb.data.model.PersonResponse

/**
 * Created by Jude on 11/September/2020
 */
interface PersonApi {

    suspend fun fetchPersonDetails(personId: Int): PersonResponse
}