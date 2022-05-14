package com.davidbronn.movietmdb.data.mapper

import com.davidbronn.movietmdb.data.model.ResultsItemResponse
import com.davidbronn.movietmdb.domain.model.CastItemModel
import com.davidbronn.movietmdb.utils.misc.Mapper
import javax.inject.Inject

class SimilarMovieMapper @Inject constructor() : Mapper<ResultsItemResponse, CastItemModel> {
    override fun map(t: ResultsItemResponse): CastItemModel {
        return CastItemModel(t.posterPath ?: "", t.title, t.id.toString(), true)
    }
}