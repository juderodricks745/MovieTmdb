package com.davidbronn.movietmdb.ui.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.davidbronn.movietmdb.domain.model.ItemModel
import com.davidbronn.movietmdb.domain.repository.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(private val repo: MoviesRepository) :
    ViewModel() {

    fun fetchMovies(): Flow<PagingData<ItemModel>> =
        repo.fetchMovies().cachedIn(viewModelScope)
}