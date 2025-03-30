package com.davidbronn.movietmdb.data.repository

import com.davidbronn.movietmdb.data.model.CastItem
import com.davidbronn.movietmdb.data.model.DetailsResponse
import com.davidbronn.movietmdb.data.model.ResultsItemResponse
import com.davidbronn.movietmdb.domain.api.DetailsApi
import com.davidbronn.movietmdb.domain.model.CastItemModel
import com.davidbronn.movietmdb.domain.model.DetailsModel
import com.davidbronn.movietmdb.domain.qualifier.IoDispatcher
import com.davidbronn.movietmdb.domain.repository.DetailsRepository
import com.davidbronn.movietmdb.utils.extensions.catchWithDispatcher
import com.davidbronn.movietmdb.utils.extensions.mapErrorResponse
import com.davidbronn.movietmdb.utils.extensions.mapItems
import com.davidbronn.movietmdb.utils.extensions.mapResponse
import com.davidbronn.movietmdb.utils.misc.Mapper
import com.davidbronn.movietmdb.utils.misc.Resource
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Created by Jude on 12/January/2020
 */
class DetailsRepositoryImpl @Inject constructor(
    @IoDispatcher private val provider: CoroutineDispatcher,
    private val api: DetailsApi,
    private val detailMapper: Mapper<DetailsResponse, DetailsModel>,
    private val castMapperModel: Mapper<CastItem, CastItemModel>,
    private val moviesMapper: Mapper<ResultsItemResponse, CastItemModel>,
    private val gson: Gson
) : DetailsRepository {

    override fun fetchMoviesCast(movieId: Int) = flow {
        val response = api.fetchMoviesCreditAsync(movieId)
        if (response.isSuccessful) {
            val moviesCast =
                response.body()?.cast?.mapItems(castMapperModel) { it.profilePath.isNullOrBlank().not() } ?: emptyList()
            emit(Resource.Success(moviesCast))
        } else {
            emit(Resource.Error(response.mapErrorResponse(gson)))
        }
    }.catchWithDispatcher(provider)

    override fun fetchSimilarMovies(movieId: Int) = flow {
        val response = api.fetchSimilarMoviesAsync(movieId)
        if (response.isSuccessful) {
            val moviesCast =
                response.body()?.results?.mapItems(moviesMapper) { !it.posterPath.isNullOrBlank() } ?: emptyList()
            emit(Resource.Success(moviesCast))
        } else {
            emit(Resource.Error(response.mapErrorResponse(gson)))
        }
    }.catchWithDispatcher(provider)

    override fun fetchMovieDetails(movieId: Int) = flow {
        val response = api.fetchMovieDetailsAsync(movieId)
        emit(response.mapResponse(detailMapper, gson))
    }.catchWithDispatcher(provider)
}