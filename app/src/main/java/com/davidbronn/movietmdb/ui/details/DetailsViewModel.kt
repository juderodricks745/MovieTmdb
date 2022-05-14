package com.davidbronn.movietmdb.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davidbronn.movietmdb.domain.model.CastItemModel
import com.davidbronn.movietmdb.domain.model.DetailsModel
import com.davidbronn.movietmdb.domain.repository.DetailsRepository
import com.davidbronn.movietmdb.utils.misc.Resource
import com.davidbronn.movietmdb.utils.misc.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val repository: DetailsRepository
) : ViewModel() {

    private val _state = SingleLiveEvent<DetailsState>()
    val state: LiveData<DetailsState> = _state

    fun fetchMovieDetails(movieID: Int) {
        viewModelScope.launch {
            repository.fetchMovieDetails(movieID)
                .collect { resource ->
                    when (resource) {
                        is Resource.Error -> {}
                        is Resource.Success -> {
                            resource.data.let { data ->
                                _state.value = DetailsState.MovieDetail(data)
                            }
                        }
                    }
                }
        }
    }

    fun fetchSimilarMovies(movieID: Int) {
        viewModelScope.launch {
            repository.fetchSimilarMovies(movieID)
                .collect { resource ->
                    when (resource) {
                        is Resource.Error -> {}
                        is Resource.Success -> {
                            _state.value = DetailsState.SimilarMovies(resource.data)
                        }
                    }
                }
        }
    }

    fun fetchCreditsAndCasts(movieID: Int) {
        viewModelScope.launch {
            repository.fetchMoviesCast(movieID)
                .collect { resource ->
                    when (resource) {
                        is Resource.Error -> {}
                        is Resource.Success -> {
                            _state.value = DetailsState.MovieCasts(resource.data)
                        }
                    }
                }
        }
    }

    sealed class DetailsState {
        data class MovieDetail(val detail: DetailsModel) : DetailsState()
        data class MovieCasts(val castModels: List<CastItemModel>) : DetailsState()
        data class SimilarMovies(val models: List<CastItemModel>) : DetailsState()
    }
}