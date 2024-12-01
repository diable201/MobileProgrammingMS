package com.example.assignment_4.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.assignment_4.model.FavoriteAnime
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteAnimeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(anime: FavoriteAnime)

    @Delete
    suspend fun removeFavorite(anime: FavoriteAnime)

    @Query("SELECT * FROM favorite_anime")
    fun getAllFavorites(): Flow<List<FavoriteAnime>>
}