package com.davidbronn.movietmdb.data.mapper

import com.davidbronn.movietmdb.data.model.PersonResponse
import com.davidbronn.movietmdb.domain.model.PersonModel
import com.davidbronn.movietmdb.utils.misc.Mapper
import javax.inject.Inject

class PersonMapper @Inject constructor() : Mapper<PersonResponse, PersonModel> {

    override fun map(t: PersonResponse): PersonModel {
        return PersonModel(
            t.birthday ?: "",
            t.deathday ?: "",
            t.placeOfBirth,
            t.alsoKnownAs.joinToString(", "),
            t.biography,
        )
    }
}