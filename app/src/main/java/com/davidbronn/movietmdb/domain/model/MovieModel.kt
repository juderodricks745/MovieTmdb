package com.davidbronn.movietmdb.domain.model

import androidx.recyclerview.widget.DiffUtil

data class ItemModel(
    val id: Int,
    val title: String,
    val posterPath: String,
) {
    companion object {

        val DIFF_CALLBACK: DiffUtil.ItemCallback<ItemModel> = object :
            DiffUtil.ItemCallback<ItemModel>() {
            override fun areItemsTheSame(oldItemResponse: ItemModel, newItemResponse: ItemModel): Boolean {
                return oldItemResponse.id == newItemResponse.id
            }

            override fun areContentsTheSame(oldItemResponse: ItemModel, newItemResponse: ItemModel): Boolean {
                return oldItemResponse == newItemResponse
            }
        }
    }
}
