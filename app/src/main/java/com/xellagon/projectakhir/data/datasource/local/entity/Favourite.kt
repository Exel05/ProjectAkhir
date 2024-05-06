package com.xellagon.projectakhir.data.datasource.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourite")
data class Favourite(
    @PrimaryKey(true)
    val id : Int,
    val image : String,
    val animal : String
)
