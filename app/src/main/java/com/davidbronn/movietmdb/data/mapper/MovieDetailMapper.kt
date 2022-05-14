package com.davidbronn.movietmdb.data.mapper

import com.davidbronn.movietmdb.data.model.DetailsResponse
import com.davidbronn.movietmdb.domain.model.DetailsModel
import com.davidbronn.movietmdb.utils.misc.Mapper
import javax.inject.Inject

class MovieDetailMapper @Inject constructor() : Mapper<DetailsResponse, DetailsModel> {

    override fun map(t: DetailsResponse): DetailsModel {
        return DetailsModel(
            t.id,
            t.title,
            t.tagline,
            t.overview,
            t.posterPath,
            t.backdropPath,
            "${t.runtime} mins",
            t.releaseDate,
            t.genres.map { it.name }.joinToString(", "),
            t.tagline.isNotBlank()
        )
    }
}