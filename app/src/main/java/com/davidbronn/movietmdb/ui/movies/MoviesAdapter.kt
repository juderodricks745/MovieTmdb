package com.davidbronn.movietmdb.ui.movies

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.davidbronn.movietmdb.databinding.LayoutMovieItemBinding
import com.davidbronn.movietmdb.domain.model.ItemModel

/**
 * Created by Jude on 12/January/2020
 */
class MoviesAdapter(val onClickEvent: (ItemModel) -> Unit) :
    PagingDataAdapter<ItemModel, MoviesAdapter.MoviesViewHolder>(ItemModel.DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val binding = LayoutMovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MoviesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        val movieItem = getItem(position)
        holder.bindItem(movieItem!!)
        holder.binding.root.setOnClickListener(holder)
    }

    inner class MoviesViewHolder(val binding: LayoutMovieItemBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        fun bindItem(item: ItemModel) {
            binding.item = item
            binding.executePendingBindings()
        }

        override fun onClick(v: View?) {
            val item = getItem(bindingAdapterPosition)
            if (item != null) {
                onClickEvent(item)
            }
        }
    }
}