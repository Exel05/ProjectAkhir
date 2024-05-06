package com.xellagon.projectakhir.di

import android.app.Application
import com.xellagon.projectakhir.data.AnimalKnowledgeRepository
import com.xellagon.projectakhir.data.AnimalKnowledgeRepositoryImpl
import com.xellagon.projectakhir.data.datasource.local.FavDao
import com.xellagon.projectakhir.data.datasource.local.FavDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.postgrest.Postgrest
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
        }
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
