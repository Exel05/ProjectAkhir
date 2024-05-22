package com.xellagon.animalknowledge.di

import android.app.Application
import com.xellagon.animalknowledge.data.AnimalKnowledgeRepository
import com.xellagon.animalknowledge.data.AnimalKnowledgeRepositoryImpl
import com.xellagon.animalknowledge.data.datasource.local.FavDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.realtime.Realtime
import io.github.jan.supabase.realtime.RealtimeChannel
import io.github.jan.supabase.realtime.channel
import io.github.jan.supabase.storage.Storage
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSupabaseClient() : SupabaseClient {
        return createSupabaseClient(
            "https://gyobuugeucchaqeuuykj.supabase.co",
            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Imd5b2J1dWdldWNjaGFxZXV1eWtqIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MTQwOTE1OTUsImV4cCI6MjAyOTY2NzU5NX0.UHmvs39Vz4jqT_fJkecgMLS9nUyOfb7YmlbxmtcoPLQ"
        ) {
            install(Auth)
            install(Postgrest)
            install(Realtime)
            install(Storage)
        }
    }

    @Provides
    @Singleton
    fun providesRealTimeClient(
        client: SupabaseClient
    ) : RealtimeChannel {
        return client.channel("animalknowledge")
    }

    @Provides
    @Singleton
    fun provideRepository(
        client: SupabaseClient,
        favDatabase: FavDatabase
    ) : AnimalKnowledgeRepository {
        return AnimalKnowledgeRepositoryImpl(client, favDatabase.getFavDao())
    }

    @Provides
    @Singleton
    fun provideFavouriteDatabase(
        app : Application
    ) : FavDatabase {
        return FavDatabase.getInstance(app)
    }
}
