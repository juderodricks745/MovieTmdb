package com.davidbronn.movietmdb.data.repository

import com.davidbronn.movietmdb.data.model.CastItem
import com.davidbronn.movietmdb.data.model.DetailsResponse
import com.davidbronn.movietmdb.data.model.ResultsItemResponse
import com.davidbronn.movietmdb.domain.api.DetailsApi
import com.davidbronn.movietmdb.domain.model.CastItemModel
import com.davidbronn.movietmdb.domain.model.DetailsModel
import com.davidbronn.movietmdb.domain.qualifier.IoDispatcher
import com.davidbronn.movietmdb.domain.repository.DetailsRepository
import com.davidbronn.movietmdb.utils.extensions.mapItems
import com.davidbronn.movietmdb.utils.extensions.safeApiCall
import com.davidbronn.movietmdb.utils.misc.Mapper
import com.davidbronn.movietmdb.utils.misc.Resource
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

/**
 * Created by Jude on 12/January/2020
 */
class DetailsRepositoryImpl @Inject constructor(
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    private val api: DetailsApi,
    private val detailMapper: Mapper<DetailsResponse, DetailsModel>,
    private val castMapperModel: Mapper<CastItem, CastItemModel>,
    private val moviesMapper: Mapper<ResultsItemResponse, CastItemModel>
) : DetailsRepository {

    override suspend fun fetchMoviesCast(movieId: Int): Resource<List<CastItemModel>> {
        return safeApiCall(dispatcher) {
            val response = api.fetchMoviesCreditAsync(movieId)
            response.cast.mapItems(castMapperModel) { it.profilePath.isNullOrBlank().not() }
        }
    }

    override suspend fun fetchSimilarMovies(movieId: Int): Resource<List<CastItemModel>> {
        return safeApiCall(dispatcher) {
            val response = api.fetchSimilarMoviesAsync(movieId)
            response.results.mapItems(moviesMapper) { !it.posterPath.isNullOrBlank() }
        }
    }

    override suspend fun fetchMovieDetails(movieId: Int): Resource<DetailsModel> {
        return safeApiCall(dispatcher) {
            val response = api.fetchMovieDetailsAsync(movieId)
            detailMapper.map(response)
        }
    }
}