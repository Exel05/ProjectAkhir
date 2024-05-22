package com.xellagon.animalknowledge.ui.screens.favourite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rmaprojects.apirequeststate.RequestState
import com.xellagon.animalknowledge.data.AnimalKnowledgeRepository
import com.xellagon.animalknowledge.data.datasource.local.entity.Favourite
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(
    private val repository: AnimalKnowledgeRepository
) : ViewModel() {

    private val _favState = MutableStateFlow<RequestState<List<Favourite>>>(RequestState.Idle)
    val favState = _favState.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        RequestState.Idle
    )

    fun getFav() {
        viewModelScope.launch {
            _favState.emit(RequestState.Idle)
            try {
                repository.getFavList().collect{
                    _favState.emit(RequestState.Success(it))
                }
            }  catch (e : Exception) {
                _favState.emit(RequestState.Error(e.message.toString()))
            }

        }
    }
    init {
        getFav()
    }

    fun deleteFav(favourite: Favourite) {
        viewModelScope.launch {
            repository.deleteFav(favourite)
        }
    }



}