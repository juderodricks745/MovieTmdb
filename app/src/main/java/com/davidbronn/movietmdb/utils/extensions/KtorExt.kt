package com.davidbronn.movietmdb.utils.extensions

import com.davidbronn.movietmdb.utils.misc.Resource
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

fun mapKtorError(exception: Throwable): Resource<Nothing> {
    return when (exception) {
        is ClientRequestException -> Resource.Error("Client Error: ${exception.message}")
        is ServerResponseException -> Resource.Error("Server Error: ${exception.message}")
        else -> Resource.Error("Network error: ${exception.message ?: "Something went wrong!"}")
    }
}

suspend fun <T> safeApiCall(
    dispatcher: CoroutineDispatcher,
    apiCall: suspend () -> T
): Resource<T> = withContext(dispatcher) {
    try {
        Resource.Success(apiCall())
    } catch (e: Exception) {
        mapKtorError(e)
    }
}