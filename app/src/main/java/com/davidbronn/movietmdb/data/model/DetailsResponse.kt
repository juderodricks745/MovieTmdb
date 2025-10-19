package com.davidbronn.movietmdb.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DetailsResponse(

    @SerialName("title")
    var title: String = "",

    @SerialName("backdrop_path")
    var backdropPath: String = "",

    @SerialName("genres")
    var genres: List<GenresItem> = emptyList(),

    @SerialName("id")
    var id: Int = -1,

    @SerialName("overview")
    var overview: String = "",

    @SerialName("runtime")
    var runtime: Int = 0,

    @SerialName("poster_path")
    var posterPath: String = "",

    @SerialName("release_date")
    var releaseDate: String = "",

    @SerialName("tagline")
    var tagline: String = "",
)

@Serializable
data class GenresItem(

    @SerialName("name")
    var name: String = ""
)