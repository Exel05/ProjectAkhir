package com.xellagon.animalknowledge.ui.screens.updateprofile

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rmaprojects.apirequeststate.RequestState
import com.xellagon.animalknowledge.data.AnimalKnowledgeRepository
import com.xellagon.animalknowledge.data.kotpref.Kotpref
import com.xellagon.animalknowledge.ui.screens.navArgs
import com.xellagon.animalknowledge.ui.screens.profile.ProfileArguments
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateProfileViewModel @Inject constructor(
    private val repository: AnimalKnowledgeRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow<RequestState<Boolean>>(RequestState.Idle)
    val state = _state.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        RequestState.Idle
    )
    val navArgs : ProfileArguments = savedStateHandle.navArgs()


    fun updateProfile(id: String, username: String) {
        viewModelScope.launch {
            _state.emit(RequestState.Loading)
            try {
                repository.updateProfile(id, username)
                _state.emit(RequestState.Success(true))
                Kotpref.id = id
                Kotpref.username = username
            } catch (e : Exception) {
                _state.emit(RequestState.Error(e.message.toString()))
            }
        }
    }

}