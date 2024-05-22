package com.xellagon.animalknowledge.ui.screens.updateanimal

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rmaprojects.apirequeststate.RequestState
import com.xellagon.animalknowledge.data.AnimalKnowledgeRepository
import com.xellagon.animalknowledge.source.Animal
import com.xellagon.animalknowledge.ui.screens.detail.DetailArguments
import com.xellagon.animalknowledge.ui.screens.navArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateAnimalViewModel @Inject constructor(
    private val repository: AnimalKnowledgeRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val navArgs: DetailArguments = savedStateHandle.navArgs()

    private val _animalState = MutableStateFlow<RequestState<Boolean>>(RequestState.Idle)
    val animalState = _animalState.asStateFlow().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        RequestState.Idle
    )

    fun updateAnimal(
        animal: Animal,
        uri: Uri?
    ) {
        viewModelScope.launch {
            if (uri == null) {
                _animalState.emitAll(
                    repository.updateAnimal(animal)
                )
            } else {
                _animalState.emit(RequestState.Loading)
                try {
                    repository.uploadFile(animal.animalName, uri).let { url ->
                        repository.updateAnimal(animal.copy(image = url)).collect()
                        _animalState.emit(RequestState.Success(true))
                    }
                } catch (e : Exception) {
                    _animalState.emit(RequestState.Error(e.message.toString()))
                }
            }
        }
    }

}