package com.davidbronn.movietmdb.utils.misc

interface Mapper<T, E> {
    fun map(t: T): E
}
