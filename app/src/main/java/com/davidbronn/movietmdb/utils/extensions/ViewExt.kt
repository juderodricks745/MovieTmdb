package com.davidbronn.movietmdb.utils.extensions

import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.davidbronn.movietmdb.R
import com.davidbronn.movietmdb.ui.LauncherActivity

fun Boolean.asVisibility() = if (this) View.VISIBLE else View.GONE

/**
 * Sets the view visible
 */
fun View.visible() {
    this.visibility = View.VISIBLE
}

/**
 * Sets the view as gone
 */
fun View.gone() {
    this.visibility = View.GONE
}

/**
 * Find the navController required. Read below for more information
 * https://vtsen.medium.com/replace-fragment-tag-with-fragmentcontainerview-causing-runtime-error-cc2e4df0f03a
 */
fun LauncherActivity.navController(): NavController {
    return (supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment).navController
}