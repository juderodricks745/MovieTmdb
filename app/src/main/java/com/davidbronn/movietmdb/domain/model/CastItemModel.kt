package com.davidbronn.movietmdb.domain.model

import androidx.recyclerview.widget.DiffUtil

/**
 * Created by Jude on 13/January/2020
 */
data class CastItemModel(
    var url: String = "",
    var title: String = "",
    var movieId: String = "",
    var isMovie: Boolean = false,
    var rating: String = ""
) {
    companion object {

        val DIFF_CALLBACK: DiffUtil.ItemCallback<CastItemModel> = object : DiffUtil.ItemCallback<CastItemModel>() {
            override fun areItemsTheSame(oldItemModel: CastItemModel, newItemModel: CastItemModel) =
                oldItemModel.movieId == newItemModel.movieId

            override fun areContentsTheSame(oldItemModel: CastItemModel, newItemModel: CastItemModel) =
                oldItemModel == newItemModel
        }
    }
}