package com.davidbronn.movietmdb.data.mapper

import com.davidbronn.movietmdb.data.model.ResultsItemResponse
import com.davidbronn.movietmdb.domain.model.ItemModel
import com.davidbronn.movietmdb.utils.misc.Mapper
import javax.inject.Inject

class MovieMapper @Inject constructor() : Mapper<ResultsItemResponse, ItemModel> {

    override fun map(t: ResultsItemResponse): ItemModel {
        return ItemModel(t.id, t.title, t.posterPath ?: "")
    }
}