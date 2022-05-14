package com.davidbronn.movietmdb.domain.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.davidbronn.movietmdb.data.mapper.MovieMapper
import com.davidbronn.movietmdb.domain.api.MoviesApi
import com.davidbronn.movietmdb.domain.model.ItemModel
import retrofit2.HttpException
import java.io.IOException

/**
 * Created by Jude on 07/September/2020
 */

class MoviesPagingSource(
    private val moviesApi: MoviesApi,
    private val movieMapper: MovieMapper
) : PagingSource<Int, ItemModel>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ItemModel> {
        val pageNumber = params.key ?: 1
        return try {
            val response = moviesApi.fetchPopularMoviesAsync(pageNumber)
            val results = response.body()?.results ?: emptyList()
            val movieResults = results.filter { it.posterPath != null }.map { movieMapper.map(it) }
            LoadResult.Page(
                data = movieResults,
                prevKey = null,
                nextKey = if (results.isEmpty()) null else pageNumber + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ItemModel>): Int? {
        return state.anchorPosition
    }
}