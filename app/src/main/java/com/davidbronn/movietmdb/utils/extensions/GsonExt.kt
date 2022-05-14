package com.davidbronn.movietmdb.utils.extensions

import com.google.gson.Gson

inline fun <reified T> String.toObject(gson: Gson): T? {
    return gson.fromJson(this, T::class.java)
}