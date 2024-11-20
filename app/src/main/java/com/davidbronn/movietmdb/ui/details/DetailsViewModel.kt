package com.davidbronn.movietmdb.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davidbronn.movietmdb.domain.model.CastItemModel
import com.davidbronn.movietmdb.domain.model.DetailsModel
import com.davidbronn.movietmdb.domain.repository.DetailsRepository
import com.davidbronn.movietmdb.utils.misc.Resource
import com.davidbronn.movietmdb.utils.misc.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val repository: DetailsRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val movieID: Int = savedStateHandle["movieID"] ?: error("movieID not found")

    private val _state = SingleLiveEvent<DetailsState>()
    val state: LiveData<DetailsState> = _state

    fun fetchAllMovieDetails() {
        viewModelScope.launch {
            combine(
                repository.fetchMovieDetails(movieID),
                repository.fetchSimilarMovies(movieID),
                repository.fetchMoviesCast(movieID),
            ) { movieDetailResource, similarMoviesResource, movieCastsResource ->
                Triple(movieDetailResource, similarMoviesResource, movieCastsResource)
            }.collect { (movieDetail, similarMovies, movieCasts) ->
                when (movieDetail) {
                    is Resource.Error -> {}
                    is Resource.Success -> {
                        _state.value = DetailsState.MovieDetail(movieDetail.data)
                    }
                }
                when (similarMovies) {
                    is Resource.Error -> {}
                    is Resource.Success -> {
                        _state.value = DetailsState.SimilarMovies(similarMovies.data)
                    }
                }
                when (movieCasts) {
                    is Resource.Error -> {}
                    is Resource.Success -> {
                        _state.value = DetailsState.MovieCasts(movieCasts.data)
                    }
                }
            }
        }
    }

    sealed class DetailsState {
        data class MovieDetail(val detail: DetailsModel) : DetailsState()
        data class SimilarMovies(val models: List<CastItemModel>) : DetailsState()
        data class MovieCasts(val castModels: List<CastItemModel>) : DetailsState()
    }
}