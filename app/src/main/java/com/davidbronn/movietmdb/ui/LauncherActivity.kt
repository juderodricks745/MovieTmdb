package com.davidbronn.movietmdb.ui

import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.davidbronn.movietmdb.R
import com.davidbronn.movietmdb.databinding.ActivityLauncherBinding
import com.davidbronn.movietmdb.domain.repository.PreferenceHelper
import com.davidbronn.movietmdb.ui.movies.IThemeChangeListener
import com.davidbronn.movietmdb.utils.extensions.navController
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LauncherActivity : AppCompatActivity(), IThemeChangeListener {

    @Inject
    lateinit var preference: PreferenceHelper
    private lateinit var navController: NavController
    private lateinit var binding: ActivityLauncherBinding
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_launcher)

        setPreferencesTheme()
        setSupportActionBar(binding.toolbar)
        navController = navController()
        appBarConfiguration = AppBarConfiguration.Builder(R.id.moviesFragment).build()
        setupActionBarWithNavController(navController, appBarConfiguration)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.moviesFragment -> {
                    binding.toolbar.inflateMenu(R.menu.menu_settings)
                }
                else -> {
                    binding.toolbar.menu.removeItem(R.id.menu_theme)
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onThemeChange() {
        when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_YES -> {
                preference.setTheme(isDark = false)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            Configuration.UI_MODE_NIGHT_NO -> {
                preference.setTheme(isDark = true)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
        }
    }

    private fun setPreferencesTheme() {
        if (preference.getTheme()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
}