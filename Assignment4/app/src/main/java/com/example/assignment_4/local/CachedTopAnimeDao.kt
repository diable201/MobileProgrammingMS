package com.example.assignment_4.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.assignment_4.model.CachedTopAnime
import kotlinx.coroutines.flow.Flow

@Dao
interface CachedTopAnimeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun cacheTopAnime(animeList: List<CachedTopAnime>)

    @Query("SELECT * FROM cached_top_anime ORDER BY cachedAt DESC")
    fun getCachedTopAnime(): Flow<List<CachedTopAnime>>

    @Query("DELETE FROM cached_top_anime")
    suspend fun clearCachedTopAnime()
}