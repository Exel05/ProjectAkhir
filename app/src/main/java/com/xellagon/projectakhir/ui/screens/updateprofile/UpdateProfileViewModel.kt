package com.xellagon.projectakhir.ui.screens.updateprofile

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rmaprojects.apirequeststate.RequestState
import com.xellagon.projectakhir.data.AnimalKnowledgeRepository
import com.xellagon.projectakhir.data.kotpref.Kotpref
import com.xellagon.projectakhir.ui.screens.navArgs
import com.xellagon.projectakhir.ui.screens.profile.ProfileArguments
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.emitAll
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
            _state.emitAll(repository.updateProfile(id, username))
            Kotpref.id = id
            Kotpref.username = username
        }
    }

}