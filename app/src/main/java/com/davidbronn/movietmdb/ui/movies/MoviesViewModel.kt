package com.davidbronn.movietmdb.ui.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.davidbronn.movietmdb.domain.model.ItemModel
import com.davidbronn.movietmdb.domain.repository.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val repo: MoviesRepository
) : ViewModel() {

    private val _moviesFlow = MutableStateFlow<PagingData<ItemModel>>(PagingData.empty())
    val moviesFlow: StateFlow<PagingData<ItemModel>> = _moviesFlow.asStateFlow()

    init {
        fetchMovies()
    }

    private fun fetchMovies() {
        viewModelScope.launch {
            repo.fetchMovies()
                .cachedIn(viewModelScope)
                .collectLatest { pagingData ->
                    _moviesFlow.value = pagingData
                }
        }
    }
}