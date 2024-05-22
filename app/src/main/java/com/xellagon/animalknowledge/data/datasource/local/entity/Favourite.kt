package com.xellagon.animalknowledge.data.datasource.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourite")
data class Favourite(
    @PrimaryKey(false)
    val id : Int,
    val image : String,
    val animal : String,
    val desc : String,
    val latin : String,
    val kingdom : String,
    val idUser : String? = null
)
