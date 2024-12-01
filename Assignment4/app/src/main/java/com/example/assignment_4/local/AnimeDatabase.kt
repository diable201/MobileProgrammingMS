package com.example.assignment_4.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.assignment_4.model.CachedTopAnime
import com.example.assignment_4.model.CachedUpcomingAnime
import com.example.assignment_4.model.FavoriteAnime
import com.example.assignment_4.model.User


@Database(
    entities = [User::class, FavoriteAnime::class, CachedTopAnime::class, CachedUpcomingAnime::class],
    version = 2,
    exportSchema = false
)
abstract class AnimeDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun favoriteAnimeDao(): FavoriteAnimeDao
    abstract fun cachedTopAnimeDao(): CachedTopAnimeDao
    abstract fun cachedUpcomingAnimeDao(): CachedUpcomingAnimeDao

    companion object {
        @Volatile
        private var INSTANCE: AnimeDatabase? = null

        fun getInstance(context: Context): AnimeDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext, AnimeDatabase::class.java, "anime_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
