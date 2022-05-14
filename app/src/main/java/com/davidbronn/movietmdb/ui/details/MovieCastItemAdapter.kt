package com.davidbronn.movietmdb.ui.details

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.davidbronn.movietmdb.databinding.LayoutMovieCastItemBinding
import com.davidbronn.movietmdb.domain.model.CastItemModel

/**
 * Created by Jude on 13/January/2020
 */
class MovieCastItemAdapter(val clickEvent: (CastItemModel) -> Unit)
    : ListAdapter<CastItemModel, MovieCastItemAdapter.MovieCastItemViewHolder>(CastItemModel.DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieCastItemViewHolder {
        val binding = LayoutMovieCastItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieCastItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieCastItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bindItem(item)
        holder.binding.root.setOnClickListener(holder)
    }

    inner class MovieCastItemViewHolder(val binding: LayoutMovieCastItemBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        fun bindItem(castItemModel: CastItemModel) {
            binding.item = castItemModel
            binding.executePendingBindings()
        }

        override fun onClick(v: View?) {
            clickEvent(getItem(bindingAdapterPosition))
        }
    }
}