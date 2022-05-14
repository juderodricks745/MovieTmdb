package com.davidbronn.movietmdb.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.davidbronn.movietmdb.data.mapper.MovieMapper
import com.davidbronn.movietmdb.domain.api.MoviesApi
import com.davidbronn.movietmdb.domain.model.ItemModel
import com.davidbronn.movietmdb.domain.pagingsource.MoviesPagingSource
import com.davidbronn.movietmdb.domain.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by Jude on 14/June/2020
 */
class MoviesRepositoryImpl @Inject constructor(
    private val moviesApi: MoviesApi,
    private val movieMapper: MovieMapper
) :
    MoviesRepository {

    override fun fetchMovies(): Flow<PagingData<ItemModel>> {
        return Pager(
            config = PagingConfig(pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = false),
            pagingSourceFactory = { MoviesPagingSource(moviesApi, movieMapper) }
        ).flow
    }

    companion object {
        private const val NETWORK_PAGE_SIZE = 20
    }
}