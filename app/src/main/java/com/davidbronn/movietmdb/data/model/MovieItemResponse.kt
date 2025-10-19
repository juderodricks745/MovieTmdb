package com.davidbronn.movietmdb.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieItemResponse(

    @SerialName("page")
	val page: Int = 0,

    @SerialName("total_pages")
	val totalPages: Int = 0,

    @SerialName("results")
	val results: List<ResultsItemResponse> = emptyList(),

    @SerialName("total_results")
	val totalResults: Int = 0
)

@Serializable
data class ResultsItemResponse(

    @SerialName("overview")
    val overview: String = "",

    @SerialName("original_language")
    val originalLanguage: String = "",

    @SerialName("original_title")
    val originalTitle: String = "",

    @SerialName("video")
    val video: Boolean = false,

    @SerialName("title")
    val title: String = "",

    @SerialName("genre_ids")
    val genreIds: List<Int> = emptyList(),

    @SerialName("poster_path")
    val posterPath: String? = null,

    @SerialName("backdrop_path")
    val backdropPath: String = "",

    @SerialName("release_date")
    val releaseDate: String = "",

    @SerialName("popularity")
    val popularity: Double = 0.0,

    @SerialName("vote_average")
    val voteAverage: Double = 0.0,

    @SerialName("id")
    val id: Int = -1,

    @SerialName("adult")
    val adult: Boolean = false,

    @SerialName("vote_count")
    val voteCount: Int = -1
)