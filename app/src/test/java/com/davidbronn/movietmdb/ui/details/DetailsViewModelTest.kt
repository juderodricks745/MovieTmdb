package com.davidbronn.movietmdb.ui.details

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateHandle
import com.davidbronn.movietmdb.domain.model.CastItemModel
import com.davidbronn.movietmdb.domain.model.DetailsModel
import com.davidbronn.movietmdb.domain.repository.DetailsRepository
import com.davidbronn.movietmdb.utils.misc.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class DetailsViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: DetailsRepository

    private lateinit var viewModel: DetailsViewModel
    private val testDispatcher = UnconfinedTestDispatcher()
    private val movieId = 123

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        val savedStateHandle = SavedStateHandle().apply {
            set("movieID", movieId)
        }
        viewModel = DetailsViewModel(repository, savedStateHandle)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when fetchAllMovieDetails is called, it should fetch movie details, similar movies and casts`() = runTest {
        // Given
        val movieDetail = DetailsModel(
            movieId = movieId,
            title = "Test Movie",
            tagLine = "Test Tagline",
            overview = "Test Overview",
            posterPath = "test/poster.jpg",
            backDropPath = "test/backdrop.jpg",
            runtime = "120 mins",
            releaseDate = "2024-01-01",
            genres = "Action, Drama",
            showTagline = true
        )
        val similarMovies = listOf(
            CastItemModel(
                movieId = "456",
                title = "Similar Movie 1",
                url = "test/url1.jpg",
                isMovie = true
            )
        )
        val movieCasts = listOf(
            CastItemModel(
                movieId = "789",
                title = "Actor 1",
                url = "test/actor1.jpg",
                isMovie = false
            )
        )

        whenever(repository.fetchMovieDetails(movieId)).thenReturn(flowOf(Resource.Success(movieDetail)))
        whenever(repository.fetchSimilarMovies(movieId)).thenReturn(flowOf(Resource.Success(similarMovies)))
        whenever(repository.fetchMoviesCast(movieId)).thenReturn(flowOf(Resource.Success(movieCasts)))

        // When
        viewModel.fetchAllMovieDetails()

        // Then
        val states = mutableListOf<DetailsViewModel.DetailsState>()
        val latch = CountDownLatch(1)
        val observer = Observer<DetailsViewModel.DetailsState> { value ->
            value.let {
                states.add(it)
                latch.countDown()
            }
        }
        viewModel.state.observeForever(observer)

        // Wait for the last state to be emitted
        latch.await(2, TimeUnit.SECONDS)

        // Verify that the last state is MovieCasts
        assertEquals(1, states.size)
        assertEquals(movieCasts, (states[0] as DetailsViewModel.DetailsState.MovieCasts).castModels)

        verify(repository).fetchMovieDetails(movieId)
        verify(repository).fetchSimilarMovies(movieId)
        verify(repository).fetchMoviesCast(movieId)
        advanceUntilIdle()

        viewModel.state.removeObserver(observer)
    }

    @Test
    fun `when repository returns error for movie details, state should not be updated`() = runTest {
        // Given
        whenever(repository.fetchMovieDetails(movieId)).thenReturn(flowOf(Resource.Error("Error fetching details")))
        whenever(repository.fetchSimilarMovies(movieId)).thenReturn(flowOf(Resource.Success(emptyList())))
        whenever(repository.fetchMoviesCast(movieId)).thenReturn(flowOf(Resource.Success(emptyList())))

        // When
        viewModel.fetchAllMovieDetails()

        // Then
        val states = mutableListOf<DetailsViewModel.DetailsState>()
        val latch = CountDownLatch(1)
        val observer = Observer<DetailsViewModel.DetailsState> { value ->
            value.let {
                states.add(it)
                latch.countDown()
            }
        }
        viewModel.state.observeForever(observer)

        // Wait for the last state to be emitted
        latch.await(2, TimeUnit.SECONDS)

        // Verify that the last state is MovieCasts with empty list
        assertEquals(1, states.size)
        assertEquals(emptyList<CastItemModel>(), (states[0] as DetailsViewModel.DetailsState.MovieCasts).castModels)

        verify(repository).fetchMovieDetails(movieId)
        verify(repository).fetchSimilarMovies(movieId)
        verify(repository).fetchMoviesCast(movieId)
        advanceUntilIdle()

        viewModel.state.removeObserver(observer)
    }
} 