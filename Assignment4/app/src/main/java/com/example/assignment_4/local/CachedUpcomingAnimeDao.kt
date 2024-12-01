package com.example.assignment_4.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.assignment_4.model.CachedUpcomingAnime
import kotlinx.coroutines.flow.Flow

@Dao
interface CachedUpcomingAnimeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun cacheUpcomingAnime(animeList: List<CachedUpcomingAnime>)

    @Query("SELECT * FROM cached_upcoming_anime ORDER BY cachedAt DESC")
    fun getCachedUpcomingAnime(): Flow<List<CachedUpcomingAnime>>

    @Query("DELETE FROM cached_upcoming_anime")
    suspend fun clearCachedUpcomingAnime()
}