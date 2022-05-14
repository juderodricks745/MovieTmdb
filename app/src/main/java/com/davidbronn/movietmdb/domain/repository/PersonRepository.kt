package com.davidbronn.movietmdb.domain.repository

import com.davidbronn.movietmdb.utils.misc.Resource
import com.davidbronn.movietmdb.domain.model.PersonModel
import kotlinx.coroutines.flow.Flow

/**
 * Created by Jude on 12/September/2020
 */
interface PersonRepository {
    fun fetchPersonDetails(personId: Int): Flow<Resource<PersonModel>>
}