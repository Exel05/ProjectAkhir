package com.xellagon.projectakhir.data.datasource.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.xellagon.projectakhir.data.datasource.local.entity.Favourite

@Database(version = 1, entities = [Favourite::class])
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
                ).build()
            }
        }
    }

}