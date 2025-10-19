package com.davidbronn.movietmdb.data.repository

import com.davidbronn.movietmdb.data.model.PersonResponse
import com.davidbronn.movietmdb.domain.api.PersonApi
import com.davidbronn.movietmdb.domain.model.PersonModel
import com.davidbronn.movietmdb.domain.qualifier.IoDispatcher
import com.davidbronn.movietmdb.domain.repository.PersonRepository
import com.davidbronn.movietmdb.utils.extensions.safeApiCall
import com.davidbronn.movietmdb.utils.misc.Mapper
import com.davidbronn.movietmdb.utils.misc.Resource
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

/**
 * Created by Jude on 11/September/2020
 */
class PersonRepositoryImpl @Inject constructor(
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    private val api: PersonApi,
    private val mapper: Mapper<PersonResponse, PersonModel>
) : PersonRepository {

    override suspend fun fetchPersonDetails(personId: Int): Resource<PersonModel> {
        return safeApiCall(dispatcher) {
            val response = api.fetchPersonDetails(personId)
            mapper.map(response)
        }
    }
}