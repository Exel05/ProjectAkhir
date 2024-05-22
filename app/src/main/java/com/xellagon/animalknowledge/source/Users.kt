package com.xellagon.animalknowledge.source

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Users(
    @SerialName("id") val id : String,
    @SerialName("username") val username : String
)