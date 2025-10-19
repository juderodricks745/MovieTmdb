package com.davidbronn.movietmdb.ui

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.recyclerview.widget.RecyclerView
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
class MovieFlowE2ETest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val activityRule = ActivityTestRule(LauncherActivity::class.java)

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun completeMovieFlow_moviesToDetailsToPersonAndBack() {
        // Step 1: Verify we start on Movies screen
        onView(withText("Movies")).check(matches(isDisplayed()))
        onView(withId(R.id.rvMovies)).check(matches(isDisplayed()))
        
        // Wait for movies to load
        Thread.sleep(4000)
        
        // Step 2: Navigate to Details screen
        onView(withId(R.id.rvMovies))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        
        // Wait for details to load
        Thread.sleep(4000)
        
        // Step 3: Verify Details screen components
        onView(withId(R.id.scrollView)).check(matches(isDisplayed()))
        onView(withId(R.id.ivBackdropPath)).check(matches(isDisplayed()))
        onView(withId(R.id.tvReleaseDate)).check(matches(isDisplayed()))
        
        // Step 4: Scroll to cast section and navigate to Person screen
        onView(withId(R.id.scrollView))
            .perform(swipeUp())
            .perform(swipeUp())
        
        // Wait for cast data to load
        Thread.sleep(2000)
        
        onView(withId(R.id.rvCast))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        
        // Wait for person screen to load
        Thread.sleep(3000)
        
        // Step 5: Verify Person screen components
        onView(withId(R.id.cvProfilePicture)).check(matches(isDisplayed()))
        onView(withId(R.id.ivPersonProfile)).check(matches(isDisplayed()))
        onView(withId(R.id.tvBiographyLabel)).check(matches(isDisplayed()))
        
        // Step 6: Navigate back to Details
        onView(isRoot()).perform(pressBack())
        
        // Verify we're back on Details screen
        onView(withId(R.id.scrollView)).check(matches(isDisplayed()))
        
        // Step 7: Navigate back to Movies
        onView(isRoot()).perform(pressBack())
        
        // Verify we're back on Movies screen
        onView(withId(R.id.rvMovies)).check(matches(isDisplayed()))
        onView(withText("Movies")).check(matches(isDisplayed()))
    }

    @Test
    fun themeToggle_worksAcrossScreens() {
        // Wait for initial load
        Thread.sleep(3000)
        
        // Test theme toggle on Movies screen
        onView(withId(R.id.menu_theme))
            .check(matches(isDisplayed()))
            .perform(click())
        
        // Navigate to details and verify theme persisted
        onView(withId(R.id.rvMovies))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        
        Thread.sleep(3000)
        
        // Details screen should still be themed correctly
        onView(withId(R.id.scrollView)).check(matches(isDisplayed()))
        
        // Navigate back and toggle theme again
        onView(isRoot()).perform(pressBack())
        
        onView(withId(R.id.menu_theme))
            .perform(click())
    }

    @Test
    fun similarMovies_navigationWorks() {
        // Navigate to details
        Thread.sleep(4000)
        onView(withId(R.id.rvMovies))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        
        Thread.sleep(4000)
        
        // Scroll to similar movies section
        onView(withId(R.id.scrollView))
            .perform(swipeUp())
            .perform(swipeUp())
            .perform(swipeUp())
        
        Thread.sleep(2000)
        
        // Click on similar movie to navigate to another detail screen
        onView(withId(R.id.rvSimilarMovies))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        
        Thread.sleep(3000)
        
        // Should be on a new details screen
        onView(withId(R.id.scrollView)).check(matches(isDisplayed()))
        onView(withId(R.id.ivBackdropPath)).check(matches(isDisplayed()))
    }

    @Test
    fun scrolling_worksOnAllScreens() {
        // Test Movies screen scrolling
        Thread.sleep(4000)
        onView(withId(R.id.rvMovies))
            .perform(swipeUp())
            .perform(swipeDown())
        
        // Navigate to Details and test scrolling
        onView(withId(R.id.rvMovies))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        
        Thread.sleep(4000)
        
        onView(withId(R.id.scrollView))
            .perform(swipeUp())
            .perform(swipeDown())
            .perform(swipeUp())
        
        // Navigate to Person and test scrolling
        onView(withId(R.id.rvCast))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        
        Thread.sleep(3000)
        
        onView(isRoot())
            .perform(swipeUp())
            .perform(swipeDown())
    }

    @Test
    fun toolbar_navigationBehaviorCorrect() {
        // Movies screen - should show menu
        Thread.sleep(3000)
        onView(withId(R.id.menu_theme)).check(matches(isDisplayed()))
        
        // Navigate to Details
        onView(withId(R.id.rvMovies))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        
        Thread.sleep(3000)
        
        // Details screen - menu should be hidden, back button should work
        onView(isRoot()).perform(pressBack())
        
        // Should be back to Movies with menu visible again
        onView(withId(R.id.menu_theme)).check(matches(isDisplayed()))
    }
}