package com.davidbronn.movietmdb

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.davidbronn.movietmdb.ui.LauncherActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class BasicUITest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1) 
    val activityRule = ActivityTestRule(LauncherActivity::class.java)

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun basicUITest_appLaunches() {
        // Simple test to verify the app launches
        Thread.sleep(2000) // Wait for activity to fully load
        
        onView(withId(R.id.toolbar))
            .check(matches(isDisplayed()))
    }

    @Test 
    fun basicUITest_moviesScreenDisplays() {
        // Verify the movies screen loads
        Thread.sleep(3000)
        
        onView(withId(R.id.rvMovies))
            .check(matches(isDisplayed()))
    }
}