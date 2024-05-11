package com.xellagon.projectakhir.ui.screens.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rmaprojects.apirequeststate.RequestState
import com.xellagon.projectakhir.data.AnimalKnowledgeRepository
import com.xellagon.projectakhir.source.Animal
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class AddViewModel @Inject constructor(
    private val repository: AnimalKnowledgeRepository
) : ViewModel() {

    private val _state = MutableStateFlow<RequestState<Animal>>(RequestState.Idle)
    val state = _state.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        RequestState.Idle
    )
    private val _photoState = MutableStateFlow<RequestState<Boolean>>(RequestState.Idle)
    val photoState = _photoState.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        RequestState.Idle
    )

    fun insert(animal: Animal) {
        viewModelScope.launch {
            _state.emitAll(repository.insertAnimal(animal))
        }
    }

    fun uploadPhoto(id: Int, file: File) {
        viewModelScope.launch {
            _photoState.emit(RequestState.Loading)
            try {
                repository.uploadFile(id, file)
            } catch (e: Exception) {
                _photoState.emit(RequestState.Error(e.message.toString()))
            }


        }

    }
}