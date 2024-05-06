package com.xellagon.projectakhir.data

import android.util.Log
import com.rmaprojects.apirequeststate.RequestState
import com.xellagon.projectakhir.data.datasource.local.FavDao
import com.xellagon.projectakhir.data.datasource.local.entity.Favourite
import com.xellagon.projectakhir.data.kotpref.Kotpref
import com.xellagon.projectakhir.source.Animal
import com.xellagon.projectakhir.source.Users
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.builtin.Email
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.Columns
import io.github.jan.supabase.postgrest.result.PostgrestResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import javax.inject.Inject
import kotlin.math.log

class AnimalKnowledgeRepositoryImpl @Inject constructor(
    private val client: SupabaseClient,
    private val favDao: FavDao
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
            val user = client.auth.currentUserOrNull()
            val publicUser = client.from("User")
                .select {
                    filter {
                        Users::id eq user?.id
                    }
                }.decodeSingle<Users>()
            Kotpref.apply {
                this.id = publicUser.id
                this.username = publicUser.username
            }
            emit(RequestState.Success(true))
        } catch (e: Exception) {
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
                this.password = password
            }
            val user = client.auth.currentUserOrNull()
            val publicUser = client.from("User")
                .select {
                    filter {
                        Users::id eq user?.id
                    }
                }.decodeSingle<Users>()
            Kotpref.apply {
                this.id = publicUser.id
                this.username = publicUser.username
            }
            Log.d("ID USER", publicUser.id)
            emit(RequestState.Success(true))
        } catch (e: Exception) {
            emit(RequestState.Error(e.toString()))
        }
    }

    override fun getFavList(): Flow<List<Favourite>> {
        TODO("Not yet implemented")
    }

    override suspend fun insertFav(favourite: Favourite) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteFav(favourite: Favourite) {
        TODO("Not yet implemented")
    }

    override fun insertAnimal(
        animal: Animal
    ): Flow<RequestState<Animal>> {
        return flow {
            emit(RequestState.Loading)
            try {
                RequestState.Success(client.from("animal_knowledge").insert(animal))
            } catch (e: Exception) {
                emit(RequestState.Error(e.message.toString()))
            }
        }
    }

    override fun updateAnimal(
        id: Int,
        image: String,
        animal: String,
        desc: String,
        latin: String,
        kingdom: String
    ): Flow<RequestState<Animal>> {
        return flow {
            client.from("animal_knowledge").update(
                update = {
                    set("image", image)
                    set("animal_name", animal)
                    set("animal_desc", desc)
                    set("animal_latin", latin)
                    set("animal_kingdom", kingdom)
                },
                request = {
                    filter {
                        eq("id", id)
                    }
                }
            ).decodeSingle<Animal>()
        }
    }

    override fun deleteAnimal(id: Int): Flow<RequestState<List<Animal>>> {
        return flow {
            emit(RequestState.Loading)
            try {
                emit(
                    RequestState.Success(
                        client.from("animal_knowledge").delete {
                            filter {
                                eq("id", id)
                            }
                        }.decodeList<Animal>()
                    )
                )
            } catch (e : Exception) {
                emit(RequestState.Error(e.message.toString()))
                Log.d("DELETE", e.toString())
            }


        }
    }

    override fun getAnimal(): Flow<RequestState<List<Animal>>> {
        return flow {
            emit(RequestState.Loading)
            try {
                emit(
                    RequestState.Success(
                        client.from("animal_knowledge").select(Columns.ALL).decodeList<Animal>()
                    )
                )
            } catch (e: Exception) {
                emit(RequestState.Error(e.message.toString()))
            }
        }
    }

}