package com.davidbronn.movietmdb.data.model

import com.google.gson.annotations.SerializedName

data class MovieCredit(

    @field:SerializedName("cast")
    val cast: List<CastItem> = emptyList(),

    @field:SerializedName("id")
    val id: Int = -1
)

data class CastItem(

    @field:SerializedName("character")
    val character: String = "",

    @field:SerializedName("name")
    val name: String = "",

    @field:SerializedName("profile_path")
    val profilePath: String? = null,

    @field:SerializedName("id")
    val id: Int = -1,
)