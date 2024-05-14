package com.xellagon.projectakhir.ui.screens.profile

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rmaprojects.apirequeststate.RequestState
import com.xellagon.projectakhir.data.AnimalKnowledgeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    repository: AnimalKnowledgeRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow<RequestState<Boolean>>(RequestState.Idle)
    val state = _state.asStateFlow().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        RequestState.Idle
    )
}