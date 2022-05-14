package com.davidbronn.movietmdb.data.repository

import com.davidbronn.movietmdb.data.model.PersonResponse
import com.davidbronn.movietmdb.domain.api.PersonApi
import com.davidbronn.movietmdb.domain.model.PersonModel
import com.davidbronn.movietmdb.domain.qualifier.IoDispatcher
import com.davidbronn.movietmdb.domain.repository.PersonRepository
import com.davidbronn.movietmdb.utils.extensions.catchWithDispatcher
import com.davidbronn.movietmdb.utils.extensions.mapResponse
import com.davidbronn.movietmdb.utils.misc.Mapper
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Created by Jude on 11/September/2020
 */
class PersonRepositoryImpl @Inject constructor(
    @IoDispatcher private val provider: CoroutineDispatcher,
    private val api: PersonApi,
    private val mapper: Mapper<PersonResponse, PersonModel>,
    private val gson: Gson
) : PersonRepository {

    override fun fetchPersonDetails(personId: Int) = flow {
        val response = api.fetchPersonDetails(personId)
        emit(response.mapResponse(mapper, gson))
    }.catchWithDispatcher(provider)
}