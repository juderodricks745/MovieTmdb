package com.davidbronn.movietmdb.ui.people

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davidbronn.movietmdb.domain.model.PersonModel
import com.davidbronn.movietmdb.domain.repository.PersonRepository
import com.davidbronn.movietmdb.utils.misc.Resource
import com.davidbronn.movietmdb.utils.misc.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PeopleViewModel @Inject constructor(
    private val repository: PersonRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val peopleID: Int = savedStateHandle["peopleID"] ?: error("peopleID not found")

    private val _state = SingleLiveEvent<PersonState>()
    val state: LiveData<PersonState> = _state

    init {
        fetchPerson()
    }

    private fun fetchPerson() {
        viewModelScope.launch {
            repository.fetchPersonDetails(peopleID).collect { resource ->
                when (resource) {
                    is Resource.Error -> {}
                    is Resource.Success -> {
                        _state.value = PersonState.PersonDetail(resource.data)
                    }
                }
            }
        }
    }

    sealed class PersonState {
        data class PersonDetail(val detail: PersonModel) : PersonState()
    }
}