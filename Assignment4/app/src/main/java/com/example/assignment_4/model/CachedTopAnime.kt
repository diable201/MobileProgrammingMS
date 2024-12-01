package com.example.assignment_4.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cached_top_anime")
data class CachedTopAnime(
    @PrimaryKey val mal_id: Int,
    val title: String,
    val synopsis: String?,
    val image_url: String,
    val episodes: Int?,
    val score: Double?,
    val cachedAt: Long
)
