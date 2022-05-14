package com.davidbronn.movietmdb.utils.extensions

import com.davidbronn.movietmdb.data.model.StatusResponse
import com.davidbronn.movietmdb.utils.misc.Mapper
import com.davidbronn.movietmdb.utils.misc.Resource
import com.google.gson.Gson
import retrofit2.Retrofit

inline fun <reified T> Retrofit.api(): T = create(T::class.java)

fun <A, B> retrofit2.Response<A>.mapResponse(mapper: Mapper<A, B>, gson: Gson): Resource<B> {
    return when (isSuccessful) {
        true -> {
            val body = body()
            if (body != null) {
                Resource.Success(mapper.map(body))
            } else {
                Resource.Error(mapErrorResponse(gson))
            }
        }
        false -> Resource.Error("Unexpected Error!")
    }
}

fun <T> retrofit2.Response<T>.mapErrorResponse(gson: Gson): String {
    val statusModel = errorBody()?.string()?.toObject<StatusResponse>(gson)
    return statusModel?.statusMessage ?: "Unexpected Error!"
}


