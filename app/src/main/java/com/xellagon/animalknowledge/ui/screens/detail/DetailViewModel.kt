package com.xellagon.animalknowledge.ui.screens.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rmaprojects.apirequeststate.RequestState
import com.xellagon.animalknowledge.data.AnimalKnowledgeRepository
import com.xellagon.animalknowledge.data.datasource.local.entity.Favourite
import com.xellagon.animalknowledge.source.Animal
import com.xellagon.animalknowledge.ui.screens.navArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
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

    val navArgs: DetailArguments = savedStateHandle.navArgs()

    fun connectToRealTime(animalId: Int) {
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

    fun getFavData() {
        viewModelScope.launch {
            _animalState.emitAll(
                flow {
                    emit(
                        RequestState.Success(
                            Animal(
                                navArgs.idUser,
                                navArgs.animal!!,
                                navArgs.desc!!,
                                navArgs.latin!!,
                                navArgs.kingdom!!,
                                navArgs.image!!,
                                navArgs.id
                            )
                        )
                    )
                }
            )
        }
    }

    fun insertFav(favourite: Favourite) {
        viewModelScope.launch {
            repository.insertFav(favourite)
            _favState.emit(RequestState.Success(true))
        }
    }


}