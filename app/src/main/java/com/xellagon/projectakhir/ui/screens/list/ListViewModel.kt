package com.xellagon.projectakhir.ui.screens.list

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rmaprojects.apirequeststate.RequestState
import com.xellagon.projectakhir.data.AnimalKnowledgeRepository
import com.xellagon.projectakhir.data.datasource.remote.tables.SupabaseTables
import com.xellagon.projectakhir.source.Animal
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jan.supabase.realtime.PostgresAction
import io.github.jan.supabase.realtime.RealtimeChannel
import io.github.jan.supabase.realtime.postgresChangeFlow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val repository: AnimalKnowledgeRepository,
) : ViewModel() {

    private val _animalState = MutableStateFlow<RequestState<List<Animal>>>(RequestState.Loading)
    val animalState = _animalState.asStateFlow().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        RequestState.Loading
    )


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