package com.davidbronn.movietmdb.utils.extensions

import com.davidbronn.movietmdb.utils.misc.Mapper

fun <A, B> List<A>.mapItems(mapper: Mapper<A, B>, predicate: (A) -> Boolean): List<B> {
    return this.filter(predicate).map { mapper.map(it) }.toList()
}