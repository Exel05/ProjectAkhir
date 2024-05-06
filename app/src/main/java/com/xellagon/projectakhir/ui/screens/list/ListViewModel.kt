package com.xellagon.projectakhir.ui.screens.list

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rmaprojects.apirequeststate.RequestState
import com.xellagon.projectakhir.data.AnimalKnowledgeRepository
import com.xellagon.projectakhir.source.Animal
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val repository: AnimalKnowledgeRepository
) : ViewModel() {

    private val _listState = MutableStateFlow<RequestState<Boolean>>(RequestState.Idle)
    val listState = _listState.asStateFlow().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        RequestState.Idle
    )

    val listAnimal = mutableStateListOf<Animal>()

    fun deleteAnimal(id : Int) {
        viewModelScope.launch {
           repository.deleteAnimal(id).collectLatest {
               if (it.isSuccess()) {
                   refresh()
               }
           }
        }
    }

    fun refresh() {
        viewModelScope.launch {
            repository.getAnimal().collectLatest {
                if (it.isSuccess()){
                    listAnimal.clear()
                    listAnimal.addAll(it.getSuccessData())
                    _listState.emit(RequestState.Success(true))
                }
            }
        }
    }


    init {
        refresh()
    }


}