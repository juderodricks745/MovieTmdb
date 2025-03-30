package com.davidbronn.movietmdb.ui.movies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.davidbronn.movietmdb.R
import com.davidbronn.movietmdb.databinding.LayoutMoviesBottomRowBinding
import com.davidbronn.movietmdb.utils.extensions.asVisibility

class ReposLoadStateViewHolder(
    private val binding: LayoutMoviesBottomRowBinding,
    retry: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.btnRetry.setOnClickListener { retry.invoke() }
    }

    fun bind(loadState: LoadState) {
        if (loadState is LoadState.Error) {
            binding.tvNetworkIssue.text = loadState.error.localizedMessage
        }
        binding.btnRetry.visibility = (loadState !is LoadState.Loading).asVisibility()
        binding.progressBar.visibility = (loadState is LoadState.Loading).asVisibility()
        binding.tvNetworkIssue.visibility = (loadState !is LoadState.Loading).asVisibility()
    }

    companion object {
        fun create(parent: ViewGroup, retry: () -> Unit): ReposLoadStateViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_movies_bottom_row, parent, false)
            val binding = LayoutMoviesBottomRowBinding.bind(view)
            return ReposLoadStateViewHolder(
                binding,
                retry
            )
        }
    }
}
