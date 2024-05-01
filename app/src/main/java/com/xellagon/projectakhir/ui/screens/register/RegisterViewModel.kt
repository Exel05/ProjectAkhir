package com.xellagon.projectakhir.ui.screens.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rmaprojects.apirequeststate.RequestState
import com.xellagon.projectakhir.data.AnimalKnowledgeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val repository: AnimalKnowledgeRepository
) : ViewModel() {

    private val _state = MutableStateFlow<RequestState<Boolean>>(RequestState.Idle)
    val state = _state.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        RequestState.Idle
    )

    fun register(username: String, email: String, password: String) {
        viewModelScope.launch {
            _state.emitAll(repository.register(username, email, password))
//            repository.register(username, email, password)
        }
    }

}