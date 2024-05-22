package com.xellagon.animalknowledge.utils

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.xellagon.animalknowledge.data.kotpref.Kotpref

object GlobalState {
    var isDarkMode by mutableStateOf(Kotpref.isDarkMode)
}