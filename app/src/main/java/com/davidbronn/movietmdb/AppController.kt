package com.davidbronn.movietmdb

import android.app.Application
import com.davidbronn.movietmdb.utils.misc.DebugTimberTree
import com.davidbronn.movietmdb.utils.misc.GlideApp
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

/**
 * Created by Jude on 20/November/2021
 */
@HiltAndroidApp
class AppController : Application() {

    override fun onCreate() {
        super.onCreate()

        // Debug only Timber
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTimberTree())
        }
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        GlideApp.get(this).onTrimMemory(level)
    }
}