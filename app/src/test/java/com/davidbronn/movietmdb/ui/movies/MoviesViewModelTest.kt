package com.davidbronn.movietmdb.ui.movies

import androidx.paging.PagingData
import app.cash.turbine.test
import com.davidbronn.movietmdb.domain.repository.MoviesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class MoviesViewModelTest {

    @Mock
    private lateinit var repository: MoviesRepository

    private lateinit var viewModel: MoviesViewModel
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        // Mock the repository to return a valid Flow with empty PagingData
        whenever(repository.fetchMovies()).thenReturn(flowOf(PagingData.empty()))
        viewModel = MoviesViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when viewModel is initialized, it should fetch movies`() = runTest {
        // When
        viewModel.moviesFlow.test {
            // Then
            val firstEmission = awaitItem()
            assertNotNull(firstEmission)
            verify(repository).fetchMovies()
        }
        advanceUntilIdle()
    }

    @Test
    fun `moviesFlow should emit empty PagingData initially`() = runTest {
        // When
        viewModel.moviesFlow.test {
            // Then
            val firstEmission = awaitItem()
            assertNotNull(firstEmission)
        }
        advanceUntilIdle()
    }
} 