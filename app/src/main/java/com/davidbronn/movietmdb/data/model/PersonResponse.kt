package com.davidbronn.movietmdb.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PersonResponse(

    @SerialName("birthday")
    var birthday: String? = null,

    @SerialName("also_known_as")
    var alsoKnownAs: List<String> = emptyList(),

    @SerialName("gender")
    var gender: Int? = null,

    @SerialName("imdb_id")
    var imdbId: String = "",

    @SerialName("known_for_department")
    var knownForDepartment: String = "",

    @SerialName("profile_path")
    var profilePath: String = "",

    @SerialName("biography")
    var biography: String = "",

    @SerialName("deathday")
    var deathday: String? = null,

    @SerialName("place_of_birth")
    var placeOfBirth: String = "",

    @SerialName("popularity")
    var popularity: Double? = null,

    @SerialName("name")
    var name: String = "",

    @SerialName("id")
    var id: Int = -1,

    @SerialName("adult")
    var adult: Boolean = false,

    @SerialName("homepage")
    var homepage: String = ""
)
