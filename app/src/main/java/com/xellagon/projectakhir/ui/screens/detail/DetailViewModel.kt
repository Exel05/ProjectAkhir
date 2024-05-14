package com.xellagon.projectakhir.ui.screens.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rmaprojects.apirequeststate.RequestState
import com.xellagon.projectakhir.data.AnimalKnowledgeRepository
import com.xellagon.projectakhir.data.datasource.local.entity.Favourite
import com.xellagon.projectakhir.source.Animal
import com.xellagon.projectakhir.ui.screens.navArgs
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
class DetailViewModel @Inject constructor(
    private val repository: AnimalKnowledgeRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _animalState = MutableStateFlow<RequestState<Animal>>(RequestState.Loading)
    val animalState = _animalState.asStateFlow().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        RequestState.Loading
    )
    private val _favState = MutableStateFlow<RequestState<Boolean>>(RequestState.Idle)
    val favState = _favState.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        RequestState.Idle
    )

    val navArgs : DetailArguments = savedStateHandle.navArgs()

    fun connectToRealTime(animalId : Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAnimal(animalId).onSuccess {
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
            repository.unSubscribeAnimalChannel()
        }
    }

    fun insertFav(favourite: Favourite) {
        viewModelScope.launch {
            repository.insertFav(favourite)
            _favState.emit(RequestState.Success(true))
        }
    }



}