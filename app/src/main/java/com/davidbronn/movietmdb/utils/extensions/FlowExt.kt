package com.davidbronn.movietmdb.utils.extensions

import com.davidbronn.movietmdb.utils.misc.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn

fun <T> Flow<Resource<T>>.catchWithDispatcher(provider: CoroutineDispatcher): Flow<Resource<T>> {
    return catch { e ->
        emit(Resource.Error(e.message ?: "Unexpected Error!"))
    }.flowOn(provider)
}