package com.davidbronn.movietmdb.ui

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.davidbronn.movietmdb.R
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class LauncherActivityTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val activityRule = ActivityTestRule(LauncherActivity::class.java)

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun launcherActivity_displaysCorrectly() {
        // Verify that the activity launches and displays the main components
        onView(withId(R.id.toolbar)).check(matches(isDisplayed()))
    }

    @Test
    fun toolbar_displaysMoviesTitle() {
        // Verify that the toolbar shows the Movies title when on the movies fragment
        onView(withText("Movies")).check(matches(isDisplayed()))
    }

    @Test
    fun toolbar_showsThemeMenu() {
        // Verify that the theme menu is displayed on the movies fragment
        onView(withId(R.id.menu_theme)).check(matches(isDisplayed()))
    }

    @Test
    fun themeToggle_clickable() {
        // Test that the theme toggle button is clickable
        onView(withId(R.id.menu_theme))
            .check(matches(isDisplayed()))
            .perform(click())
    }

    @Test
    fun navigation_initialFragment_isMoviesFragment() {
        // Verify that the initial fragment displayed is the MoviesFragment
        onView(withId(R.id.moviesFragment)).check(matches(isDisplayed()))
    }

    @Test
    fun supportActionBar_isSetup() {
        // Verify that the support action bar is properly set up
        onView(withId(R.id.toolbar)).check(matches(isDisplayed()))
    }
}