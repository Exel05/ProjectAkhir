package com.xellagon.animalknowledge.data

import android.net.Uri
import com.rmaprojects.apirequeststate.RequestState
import com.xellagon.animalknowledge.data.datasource.local.entity.Favourite
import com.xellagon.animalknowledge.source.Animal
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
        animal: Animal,
    ) : Flow<RequestState<Boolean>>

    fun deleteAnimal(
        id: Int
    ) : Flow<RequestState<List<Animal>>>


    suspend fun updateProfile(id: String, username: String) : Flow<RequestState<Boolean>>

    suspend fun getAnimal(animalId : Int) : Result<Flow<Animal>>

    suspend fun getAllAnimal() : Result<Flow<List<Animal>>>

    suspend fun unSubscribeChannel()

    suspend fun unSubscribeAnimalChannel()

    suspend fun uploadFile(animalName : String, file : Uri): String



}