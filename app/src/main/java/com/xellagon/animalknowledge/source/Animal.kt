package com.xellagon.animalknowledge.source

import com.xellagon.animalknowledge.data.kotpref.Kotpref
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Animal(
    @SerialName("id_user") val idUser : String? = null,
    @SerialName("animal_name") val animalName : String,
    @SerialName("animal_desc") val animalDesc : String,
    @SerialName("animal_latin") val animalLatin : String,
    @SerialName("animal_kingdom") val animalKingdom : String,
    @SerialName("image") val image : String,
    @SerialName("id") val id : Int? = null,
)
