package com.davidbronn.movietmdb.domain.repository

import com.davidbronn.movietmdb.utils.misc.Resource
import com.davidbronn.movietmdb.domain.model.PersonModel

/**
 * Created by Jude on 12/September/2020
 */
interface PersonRepository {
    suspend fun fetchPersonDetails(personId: Int): Resource<PersonModel>
}