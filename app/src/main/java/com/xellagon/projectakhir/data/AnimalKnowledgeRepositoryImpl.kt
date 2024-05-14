package com.xellagon.projectakhir.data

import android.net.Uri
import android.util.Log
import com.rmaprojects.apirequeststate.RequestState
import com.xellagon.projectakhir.data.datasource.local.FavDao
import com.xellagon.projectakhir.data.datasource.local.entity.Favourite
import com.xellagon.projectakhir.data.datasource.remote.tables.SupabaseTables
import com.xellagon.projectakhir.data.kotpref.Kotpref
import com.xellagon.projectakhir.source.Animal
import com.xellagon.projectakhir.source.Users
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.annotations.SupabaseExperimental
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.builtin.Email
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.Columns
import io.github.jan.supabase.postgrest.result.PostgrestResult
import io.github.jan.supabase.realtime.channel
import io.github.jan.supabase.realtime.postgresListDataFlow
import io.github.jan.supabase.realtime.postgresSingleDataFlow
import io.github.jan.supabase.realtime.realtime
import io.github.jan.supabase.storage.UploadSignedUrl
import io.github.jan.supabase.storage.storage
import io.github.jan.supabase.storage.upload
import io.github.jan.supabase.storage.uploadAsFlow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import java.io.File
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

    override fun getFavList(): Flow<List<Favourite>> = flow {
        emitAll(favDao.getFavList())
    }

    override suspend fun insertFav(favourite: Favourite) {
        favDao.insertFav(favourite)
    }

    override suspend fun deleteFav(favourite: Favourite) {
        favDao.deleteFav(favourite)
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
    ): Flow<RequestState<Boolean>> {
        return flow {
            emit(RequestState.Loading)
            try {
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
                )
                emit(RequestState.Success(true))
            } catch (e: Exception) {
                emit(RequestState.Error(e.message.toString()))
            }

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
            } catch (e: Exception) {
                emit(RequestState.Error(e.message.toString()))
                Log.d("DELETE", e.toString())
            }
        }
    }

    override suspend fun updateProfile(id: String, username: String): Flow<RequestState<Boolean>> {
        return flow {
            client.from("User").update(
                update = {
                    set("username", username)
                },
                request = {
                    filter {
                        eq("id", id)
                    }
                }
            )
        }
    }

    private val getAnimalChannel = client.channel("getAnimalChannel")
    private val animalChannel = client.channel("animalChanel")

    @OptIn(SupabaseExperimental::class)
    override suspend fun getAnimal(animalId: Int): Result<Flow<Animal>> {
        val data = getAnimalChannel.postgresSingleDataFlow(
            schema = "public",
            table = SupabaseTables.ANIMAL_TABLE,
            primaryKey = Animal::id
        ) {
            Animal::id eq animalId
        }.flowOn(Dispatchers.IO)

        getAnimalChannel.subscribe()

        return Result.success(data)

    }


    @OptIn(SupabaseExperimental::class)
    override suspend fun getAllAnimal(): Result<Flow<List<Animal>>> {
        animalChannel.unsubscribe()
        val data = animalChannel.postgresListDataFlow(
            schema = "public",
            table = SupabaseTables.ANIMAL_TABLE,
            primaryKey = Animal::id
        ).flowOn(Dispatchers.IO)

        animalChannel.subscribe()

        return Result.success(data)
    }

    override suspend fun unSubscribeChannel() {
        animalChannel.unsubscribe()
        client.realtime.removeChannel(animalChannel)
    }

    override suspend fun unSubscribeAnimalChannel() {
        getAnimalChannel.unsubscribe()
        client.realtime.removeChannel(getAnimalChannel)
    }

    override suspend fun uploadFile(animalName: String, file: Uri): String {
        client.storage.from("photos").upload("${Kotpref.username}/$animalName", file, true)
        val result = client.storage.from("photos").publicUrl("${Kotpref.username}/$animalName")
        Log.d("ANIMAL NAME", "ANIMAL NAME")
        return result
    }


}