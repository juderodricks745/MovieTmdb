package com.davidbronn.movietmdb.ui.people

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.asFlow
import app.cash.turbine.test
import com.davidbronn.movietmdb.domain.model.PersonModel
import com.davidbronn.movietmdb.domain.repository.PersonRepository
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

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class PeopleViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: PersonRepository

    private lateinit var viewModel: PeopleViewModel
    private val testDispatcher = UnconfinedTestDispatcher()
    private val testPersonId = 123

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when viewModel is initialized, it should fetch person details`() = runTest {
        // Given
        val savedStateHandle = SavedStateHandle().apply {
            set("peopleID", testPersonId)
        }
        val mockPerson = PersonModel(
            birthDay = "1990-01-01",
            deathDay = "",
            placeOfBirth = "Test City",
            alsoKnownAs = "Test Alias",
            biography = "Test biography"
        )
        whenever(repository.fetchPersonDetails(testPersonId))
            .thenReturn(flowOf(Resource.Success(mockPerson)))
        
        // When
        viewModel = PeopleViewModel(repository, savedStateHandle)
        viewModel.state.asFlow().test {
            // Then
            val state = awaitItem()
            assertEquals(PeopleViewModel.PersonState.PersonDetail(mockPerson), state)
            verify(repository).fetchPersonDetails(testPersonId)
        }
        advanceUntilIdle()
    }

    @Test
    fun `when repository returns error, state should not be updated`() = runTest {
        // Given
        val savedStateHandle = SavedStateHandle().apply {
            set("peopleID", testPersonId)
        }
        whenever(repository.fetchPersonDetails(testPersonId))
            .thenReturn(flowOf(Resource.Error("Test error")))
        
        // When
        viewModel = PeopleViewModel(repository, savedStateHandle)
        viewModel.state.asFlow().test {
            // Then
            // No state updates should occur for error cases
            verify(repository).fetchPersonDetails(testPersonId)
        }
        advanceUntilIdle()
    }

    @Test(expected = IllegalStateException::class)
    fun `when peopleID is not provided in SavedStateHandle, should throw exception`() {
        // Given
        val emptySavedStateHandle = SavedStateHandle()

        // When/Then
        PeopleViewModel(repository, emptySavedStateHandle)
    }

    @Test
    fun `when repository returns empty person details, state should be updated with empty fields`() = runTest {
        // Given
        val savedStateHandle = SavedStateHandle().apply {
            set("peopleID", testPersonId)
        }
        val emptyPerson = PersonModel()
        whenever(repository.fetchPersonDetails(testPersonId))
            .thenReturn(flowOf(Resource.Success(emptyPerson)))
        
        // When
        viewModel = PeopleViewModel(repository, savedStateHandle)
        viewModel.state.asFlow().test {
            // Then
            val state = awaitItem()
            assertEquals(PeopleViewModel.PersonState.PersonDetail(emptyPerson), state)
            verify(repository).fetchPersonDetails(testPersonId)
        }
        advanceUntilIdle()
    }
} 