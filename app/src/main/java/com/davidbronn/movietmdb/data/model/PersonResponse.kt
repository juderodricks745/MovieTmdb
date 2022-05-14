package com.davidbronn.movietmdb.data.model

import com.google.gson.annotations.SerializedName

data class PersonResponse(

    @field:SerializedName("birthday")
    var birthday: String? = null,

    @field:SerializedName("also_known_as")
    var alsoKnownAs: List<String> = emptyList(),

    @field:SerializedName("gender")
    var gender: Int? = null,

    @field:SerializedName("imdb_id")
    var imdbId: String = "",

    @field:SerializedName("known_for_department")
    var knownForDepartment: String = "",

    @field:SerializedName("profile_path")
    var profilePath: String = "",

    @field:SerializedName("biography")
    var biography: String = "",

    @field:SerializedName("deathday")
    var deathday: String? = null,

    @field:SerializedName("place_of_birth")
    var placeOfBirth: String = "",

    @field:SerializedName("popularity")
    var popularity: Double? = null,

    @field:SerializedName("name")
    var name: String = "",

    @field:SerializedName("id")
    var id: Int = -1,

    @field:SerializedName("adult")
    var adult: Boolean = false,

    @field:SerializedName("homepage")
    var homepage: String = ""
)
