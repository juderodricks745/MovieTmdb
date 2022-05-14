package com.davidbronn.movietmdb.domain.repository

/**
 * Created by Jude on 17/June/2020
 */
interface PreferenceHelper {
    fun getTheme(): Boolean
    fun setTheme(isDark: Boolean)
}