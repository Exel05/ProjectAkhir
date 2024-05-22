package com.xellagon.animalknowledge.ui.screens.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rmaprojects.apirequeststate.RequestState
import com.xellagon.animalknowledge.data.AnimalKnowledgeRepository
import com.xellagon.animalknowledge.source.Animal
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val repository: AnimalKnowledgeRepository,
) : ViewModel() {

    private val _animalState = MutableStateFlow<RequestState<List<Animal>>>(RequestState.Loading)
    val animalState = _animalState.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private val _searchAnimal = MutableStateFlow("")
    val searchAnimal = _searchAnimal.asStateFlow().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        ""
    )
    fun onToogleSearch() {
        _isSearching.value = !_isSearching.value
        if (!_isSearching.value) {
            onSearchAnimalChange("")
        }
    }
    fun onSearchAnimalChange(animal: String) {
        _searchAnimal.value = animal
    }
    fun deleteAnimal(id : Int) {
        viewModelScope.launch {
           repository.deleteAnimal(id).collect()
        }
    }
    fun connextToRealTime() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllAnimal().onSuccess {
                it.onEach {
                    _animalState.emit(RequestState.Success(it))
                }.collect()
            }.onFailure {
                _animalState.emit(RequestState.Error(it.message.toString()))
            }
        }
    }
    fun leaveRealTimeChannel() {
        viewModelScope.launch {
            repository.unSubscribeChannel()
        }
    }


}