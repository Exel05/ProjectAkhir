package com.xellagon.animalknowledge.ui.screens.profile

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.xellagon.animalknowledge.data.AnimalKnowledgeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    repository: AnimalKnowledgeRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

}