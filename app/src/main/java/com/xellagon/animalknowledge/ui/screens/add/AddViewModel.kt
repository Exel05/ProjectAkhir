package com.xellagon.animalknowledge.ui.screens.add

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rmaprojects.apirequeststate.RequestState
import com.xellagon.animalknowledge.data.AnimalKnowledgeRepository
import com.xellagon.animalknowledge.data.kotpref.Kotpref
import com.xellagon.animalknowledge.source.Animal
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AddViewModel @Inject constructor(
    private val repository: AnimalKnowledgeRepository
) : ViewModel() {

    private val _state = MutableStateFlow<RequestState<Boolean>>(RequestState.Idle)
    val state = _state.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        RequestState.Idle
    )

    fun insert(animal: Animal, file: Uri? = null) {
        viewModelScope.launch {
            _state.emit(RequestState.Loading)
            if (file == null) {
                try {
                    repository.insertAnimal(animal).collect()
                    _state.emit(RequestState.Success(true))
                } catch (e : Exception) {
                    _state.emit(RequestState.Error(e.message.toString()))
                }
            } else {
                try {
                    repository.uploadFile("${Kotpref.username}/${animal.animalName}.png", file).let { url ->
                         repository.insertAnimal(animal.copy(image = url)).collect()
                        _state.emit(RequestState.Success(true))
                    }
                } catch (e: Exception) {
                    _state.emit(RequestState.Error(e.message.toString()))
                }
            }

        }
    }
}