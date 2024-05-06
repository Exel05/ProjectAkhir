package com.xellagon.projectakhir.ui.screens.updateanimal

import androidx.lifecycle.ViewModel
import com.xellagon.projectakhir.data.AnimalKnowledgeRepository
import com.xellagon.projectakhir.source.Animal
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UpdateAnimalViewModel @Inject constructor(
    private val repository: AnimalKnowledgeRepository
) : ViewModel() {

    fun updateAnimal(id : Int, image : String, animal : String, desc : String, latin : String, kingdom : String) {
        repository.updateAnimal(id, image, animal, desc, latin, kingdom)
    }

}