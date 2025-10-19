package com.davidbronn.movietmdb

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import dagger.hilt.android.testing.HiltTestApplication

/**
 * Custom test runner for the MovieTmdb app that uses HiltTestApplication
 * This ensures that Hilt components are properly initialized for instrumentation tests
 */
class MovieTmdbTestRunner : AndroidJUnitRunner() {

    override fun newApplication(cl: ClassLoader?, name: String?, context: Context?): Application {
        return super.newApplication(cl, HiltTestApplication::class.java.name, context)
    }
}