package com.xellagon.projectakhir.data

import android.util.Log
import com.rmaprojects.apirequeststate.RequestState
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.builtin.Email
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import javax.inject.Inject
import kotlin.math.log

class AnimalKnowledgeRepositoryImpl @Inject constructor(
    private val client : SupabaseClient
) : AnimalKnowledgeRepository {

    override suspend fun register(
        username: String,
        email: String,
        password: String
    ): Flow<RequestState<Boolean>> = flow {
        emit(RequestState.Loading)
        try {
            client.auth.signUpWith(Email) {
                this.email = email
                this.password = password
                data = buildJsonObject {
                    put("username", "$username")
                }
            }
            emit(RequestState.Success(true))
        } catch (e : Exception) {
            emit(RequestState.Error(e.toString()))
            Log.d("REPOSITORY", e.toString())
        }
    }

    override suspend fun login(
        email: String,
        password: String
    ): Flow<RequestState<Boolean>> = flow {
        emit(RequestState.Loading)
        try {
            client.auth.signInWith(Email) {
                this.email = email
                this.password = email
            }
            emit(RequestState.Success(true))
        }
        catch (e : Exception) {
            emit(RequestState.Error(e.toString()))
        }
    }

}



//    override fun register(
//        username: String,
//        email: String,
//        password: String
//    ): Flow<RequestState<Boolean>> = flow {
//    emit(RequestState.Loading)
//        try {
//            client.auth.signUpWith(Email) {
//                this.email = email
//                this.password = password
//                data = buildJsonObject {
//                    put("username", "$username")
//                    put("email", "$email")
//                    put("password", "$password")
//                }
//            }
//        } catch (e : Exception) {
//
//        }
//    }