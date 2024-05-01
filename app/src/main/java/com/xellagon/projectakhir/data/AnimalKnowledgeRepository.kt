package com.xellagon.projectakhir.data

import com.rmaprojects.apirequeststate.RequestState
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

}