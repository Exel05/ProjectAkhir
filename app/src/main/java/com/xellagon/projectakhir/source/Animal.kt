package com.xellagon.projectakhir.source

import androidx.room.PrimaryKey
import com.xellagon.projectakhir.data.datasource.local.entity.Favourite
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Animal(
    @SerialName("id_user") val idUser : String,
    @SerialName("animal_name") val animalName : String,
    @SerialName("animal_desc") val animalDesc : String,
    @SerialName("animal_latin") val animalLatin : String,
    @SerialName("animal_kingdom") val animalKingdom : String,
    @SerialName("image") val image : String? = null,
    @SerialName("id") val id : Int? = null,
)
