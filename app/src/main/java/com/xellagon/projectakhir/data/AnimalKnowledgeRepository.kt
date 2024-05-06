package com.xellagon.projectakhir.data

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rmaprojects.apirequeststate.RequestState
import com.xellagon.projectakhir.data.datasource.local.entity.Favourite
import com.xellagon.projectakhir.source.Animal
import io.github.jan.supabase.postgrest.result.PostgrestResult
import kotlinx.coroutines.flow.Flow

interface AnimalKnowledgeRepository {

    suspend fun register(
        username : String,
        email : String,
        password : String
    ) : Flow<RequestState<Boolean>>

    suspend fun login(
        email: String,
        password: String
    ) : Flow<RequestState<Boolean>>

    fun getFavList(): Flow<List<Favourite>>

    suspend fun insertFav(favourite: Favourite)

    suspend fun deleteFav(favourite: Favourite)

    fun insertAnimal(
        animal : Animal
    ) : Flow<RequestState<Animal>>

    fun updateAnimal(
        id : Int,
        image : String,
        animal : String,
        desc : String,
        latin : String,
        kingdom : String
    ) : Flow<RequestState<Animal>>

    fun deleteAnimal(
        id: Int
    ) : Flow<RequestState<List<Animal>>>

    fun getAnimal() : Flow<RequestState<List<Animal>>>

}