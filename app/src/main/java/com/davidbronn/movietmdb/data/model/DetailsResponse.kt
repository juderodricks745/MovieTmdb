package com.davidbronn.movietmdb.data.model

import com.google.gson.annotations.SerializedName

data class DetailsResponse(

    @field:SerializedName("title")
    var title: String = "",

    @field:SerializedName("backdrop_path")
    var backdropPath: String = "",

    @field:SerializedName("genres")
    var genres: List<GenresItem> = emptyList(),

    @field:SerializedName("id")
    var id: Int = -1,

    @field:SerializedName("overview")
    var overview: String = "",

    @field:SerializedName("runtime")
    var runtime: Int = 0,

    @field:SerializedName("poster_path")
    var posterPath: String = "",

    @field:SerializedName("release_date")
    var releaseDate: String = "",

    @field:SerializedName("tagline")
    var tagline: String = "",
)

data class GenresItem(

    @field:SerializedName("name")
    var name: String = ""
)