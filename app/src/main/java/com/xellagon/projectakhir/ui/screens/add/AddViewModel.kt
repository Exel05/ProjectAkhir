package com.xellagon.projectakhir.ui.screens.add

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rmaprojects.apirequeststate.RequestState
import com.xellagon.projectakhir.data.AnimalKnowledgeRepository
import com.xellagon.projectakhir.data.kotpref.Kotpref
import com.xellagon.projectakhir.source.Animal
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.io.InputStream
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
    private val _photoState = MutableStateFlow<RequestState<String>>(RequestState.Idle)
    val photoState = _photoState.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        RequestState.Idle
    )

    fun insert(animal: Animal, file: Uri? = null) {
        viewModelScope.launch {
            if (file == null) {
                _state.emitAll(repository.insertAnimal(animal))
            } else {
                try {
                    repository.uploadFile("${Kotpref.username}/${animal.animalName}.png", file).let { url ->
                        val data = repository.insertAnimal(animal.copy(image = url))
                        Log.d("URL", url.toString())
                        _state.emitAll(data)
                    }
                } catch (e: Exception) {
                    _photoState.emit(RequestState.Error(e.message.toString()))
                }
            }

        }
    }

    @Throws(IOException::class)
    fun getBytes(inputStream: InputStream): ByteArray? {
        val byteBuffer = ByteArrayOutputStream()
        val bufferSize = 1024
        val buffer = ByteArray(bufferSize)
        var len = 0
        while (inputStream.read(buffer).also { len = it } != -1) {
            byteBuffer.write(buffer, 0, len)
        }
        return byteBuffer.toByteArray()
    }
}