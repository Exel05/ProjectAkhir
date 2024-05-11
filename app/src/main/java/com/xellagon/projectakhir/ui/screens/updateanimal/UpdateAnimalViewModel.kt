package com.xellagon.projectakhir.ui.screens.updateanimal

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rmaprojects.apirequeststate.RequestState
import com.xellagon.projectakhir.data.AnimalKnowledgeRepository
import com.xellagon.projectakhir.source.Animal
import com.xellagon.projectakhir.ui.screens.detail.DetailArguments
import com.xellagon.projectakhir.ui.screens.navArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateAnimalViewModel @Inject constructor(
    private val repository: AnimalKnowledgeRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val navArgs: DetailArguments = savedStateHandle.navArgs()

    private val _animalState = MutableStateFlow<RequestState<Boolean>>(RequestState.Loading)
    val animalState = _animalState.asStateFlow().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        RequestState.Loading
    )

    fun updateAnimal(
        id: Int,
        image: String,
        animal: String,
        desc: String,
        latin: String,
        kingdom: String,
    ) {
        viewModelScope.launch {
            _animalState.emitAll(
                repository.updateAnimal(id, image, animal, desc, latin, kingdom)
            )

        }
    }

}