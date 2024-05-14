package com.xellagon.projectakhir.data.datasource.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.xellagon.projectakhir.data.datasource.local.entity.Favourite
import kotlinx.coroutines.flow.Flow

@Dao
interface FavDao {
    @Query("SELECT * FROM favourite")
    fun getFavList(): Flow<List<Favourite>>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFav(bookmark: Favourite)
    @Delete
    suspend fun deleteFav(bookmark: Favourite)
}