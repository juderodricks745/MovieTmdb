package com.davidbronn.movietmdb.data.repository

import android.content.Context
import com.davidbronn.movietmdb.domain.repository.PreferenceHelper
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Jude on 17/May/2020
 */
@Singleton
class PreferenceHelperImpl @Inject constructor(@ApplicationContext context: Context) : PreferenceHelper {

    private val prefs = context.getSharedPreferences("default", Context.MODE_PRIVATE)

    override fun getTheme(): Boolean = prefs.getBoolean(IS_DARK, false)

    override fun setTheme(isDark: Boolean) = prefs.edit().putBoolean(IS_DARK, isDark).apply()

    companion object {
        const val IS_DARK = "is_dark"
    }
}