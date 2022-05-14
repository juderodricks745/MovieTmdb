package com.davidbronn.movietmdb.data.mapper

import com.davidbronn.movietmdb.data.model.CastItem
import com.davidbronn.movietmdb.domain.model.CastItemModel
import com.davidbronn.movietmdb.utils.misc.Mapper
import javax.inject.Inject

class MovieCastMapper @Inject constructor() : Mapper<CastItem, CastItemModel> {

    override fun map(t: CastItem): CastItemModel {
        return CastItemModel(t.profilePath ?: "", t.name, t.id.toString(), false)
    }
}