package com.davidbronn.movietmdb.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieCredit(

    @SerialName("cast")
    val cast: List<CastItem> = emptyList(),

    @SerialName("id")
    val id: Int = -1
)

@Serializable
data class CastItem(

    @SerialName("character")
    val character: String = "",

    @SerialName("name")
    val name: String = "",

    @SerialName("profile_path")
    val profilePath: String? = null,

    @SerialName("id")
    val id: Int = -1,
)