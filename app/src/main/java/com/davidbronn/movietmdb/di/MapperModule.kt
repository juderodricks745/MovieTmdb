package com.davidbronn.movietmdb.di

import com.davidbronn.movietmdb.data.mapper.*
import com.davidbronn.movietmdb.data.model.CastItem
import com.davidbronn.movietmdb.data.model.DetailsResponse
import com.davidbronn.movietmdb.data.model.PersonResponse
import com.davidbronn.movietmdb.data.model.ResultsItemResponse
import com.davidbronn.movietmdb.domain.model.CastItemModel
import com.davidbronn.movietmdb.domain.model.DetailsModel
import com.davidbronn.movietmdb.domain.model.ItemModel
import com.davidbronn.movietmdb.domain.model.PersonModel
import com.davidbronn.movietmdb.utils.misc.Mapper
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class MapperModule {

    @Binds
    abstract fun bindsDetailMapper(mapper: MovieDetailMapper): Mapper<DetailsResponse, DetailsModel>

    @Binds
    abstract fun bindsCastsMapper(mapper: MovieCastMapper): Mapper<CastItem, CastItemModel>

    @Binds
    abstract fun bindSimilarMoviesMapper(mapper: SimilarMovieMapper): Mapper<ResultsItemResponse, CastItemModel>

    @Binds
    abstract fun bindsMovieMapper(mapper: MovieMapper): Mapper<ResultsItemResponse, ItemModel>

    @Binds
    abstract fun bindsPersonMapper(mapper: PersonMapper): Mapper<PersonResponse, PersonModel>
}
