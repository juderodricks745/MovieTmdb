package com.davidbronn.movietmdb.domain.model

data class DetailsModel(
    val movieId: Int = 0,
    val title: String = "",
    val tagLine: String = "",
    val overview: String = "",
    val posterPath: String = "",
    val backDropPath: String = "",
    val runtime: String = "",
    val releaseDate: String = "",
    val genres: String = "",
    val showTagline: Boolean = false,
)
