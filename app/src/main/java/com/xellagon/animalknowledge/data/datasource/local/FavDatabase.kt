package com.xellagon.animalknowledge.data.datasource.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.xellagon.animalknowledge.data.datasource.local.entity.Favourite

@Database(version = 3, entities = [Favourite::class], exportSchema = true)
abstract class FavDatabase : RoomDatabase() {
    abstract fun getFavDao() :FavDao
    companion object {
        @Volatile
        private var INSTANCE : FavDatabase? = null
        fun getInstance(context: Context) : FavDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context,
                    FavDatabase::class.java,
                    "favourite.db"
                ).fallbackToDestructiveMigration().build()
            }
        }
    }
}