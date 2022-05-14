package com.davidbronn.movietmdb.utils.misc

import timber.log.Timber

class DebugTimberTree : Timber.DebugTree() {

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        super.log(priority, "MovieTmdb_$tag", message, t)
    }

    override fun createStackElementTag(element: StackTraceElement): String {
        return String.format(
            "%s:%s",
            element.methodName,
            super.createStackElementTag(element)
        )
    }
}