package com.example.assignment_4.repository

import android.content.Context
import com.example.assignment_4.local.CachedTopAnimeDao
import com.example.assignment_4.local.CachedUpcomingAnimeDao
import com.example.assignment_4.local.FavoriteAnimeDao
import com.example.assignment_4.local.UserDao
import com.example.assignment_4.model.Anime
import com.example.assignment_4.model.FavoriteAnime
import com.example.assignment_4.model.User
import com.example.assignment_4.remote.AnimeApiService
import com.example.assignment_4.remote.NetworkUtils
import com.example.assignment_4.utils.toAnime
import com.example.assignment_4.utils.toCachedTopAnime
import com.example.assignment_4.utils.toCachedUpcomingAnime
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class AnimeRepository @Inject constructor(
    private val apiService: AnimeApiService,
    private val userDao: UserDao,
    private val favoriteAnimeDao: FavoriteAnimeDao,
    private val cachedTopAnimeDao: CachedTopAnimeDao,
    private val cachedUpcomingAnimeDao: CachedUpcomingAnimeDao,
    @ApplicationContext private val context: Context
) {

    companion object {
        private const val CACHE_EXPIRATION_MINUTES = 60
    }

    suspend fun getTopAnime(): List<Anime> {
        return withContext(Dispatchers.IO) {
            val cachedList =
                cachedTopAnimeDao.getCachedTopAnime().map { list -> list.map { it.toAnime() } }
                    .first()
            val lastCacheTime = cachedTopAnimeDao.getCachedTopAnime().map { list ->
                if (list.isNotEmpty()) list.first().cachedAt else 0L
            }.first()

            val currentTime = System.currentTimeMillis()
            val isCacheValid =
                TimeUnit.MILLISECONDS.toMinutes(currentTime - lastCacheTime) < CACHE_EXPIRATION_MINUTES

            if (isCacheValid && cachedList.isNotEmpty()) {
                cachedList
            } else {
                if (NetworkUtils.hasNetwork(context)) {
                    val response = apiService.getTopAnime()
                    val animeList = response.data

                    cachedTopAnimeDao.clearCachedTopAnime()
                    cachedTopAnimeDao.cacheTopAnime(animeList.map { it.toCachedTopAnime(currentTime) })

                    animeList
                } else {
                    cachedList
                }
            }
        }
    }

    suspend fun getUpcomingAnime(): List<Anime> {
        return withContext(Dispatchers.IO) {
            val cachedList = cachedUpcomingAnimeDao.getCachedUpcomingAnime()
                .map { list -> list.map { it.toAnime() } }.first()
            val lastCacheTime = cachedUpcomingAnimeDao.getCachedUpcomingAnime().map { list ->
                if (list.isNotEmpty()) list.first().cachedAt else 0L
            }.first()

            val currentTime = System.currentTimeMillis()
            val isCacheValid =
                TimeUnit.MILLISECONDS.toMinutes(currentTime - lastCacheTime) < CACHE_EXPIRATION_MINUTES

            if (isCacheValid && cachedList.isNotEmpty()) {
                cachedList
            } else {
                if (NetworkUtils.hasNetwork(context)) {
                    val response = apiService.getUpcomingAnime()
                    val animeList = response.data

                    cachedUpcomingAnimeDao.clearCachedUpcomingAnime()
                    cachedUpcomingAnimeDao.cacheUpcomingAnime(animeList.map {
                        it.toCachedUpcomingAnime(
                            currentTime
                        )
                    })

                    animeList
                } else {
                    cachedList
                }
            }
        }
    }

    suspend fun registerUser(user: User) {
        userDao.registerUser(user)
    }

    suspend fun getUser(username: String): User? {
        return userDao.getUser(username)
    }

    suspend fun addFavorite(anime: FavoriteAnime) {
        favoriteAnimeDao.addFavorite(anime)
    }

    suspend fun removeFavorite(anime: FavoriteAnime) {
        favoriteAnimeDao.removeFavorite(anime)
    }

    fun getAllFavorites(): kotlinx.coroutines.flow.Flow<List<FavoriteAnime>> {
        return favoriteAnimeDao.getAllFavorites()
    }

    suspend fun updateUserProfileImage(username: String, profileImageUri: String?) {
        val user = userDao.getUser(username)
        if (user != null) {
            val updatedUser = user.copy(profileImageUri = profileImageUri)
            userDao.updateUser(updatedUser)
        }
    }

    suspend fun getUserProfileImage(username: String): String? {
        return userDao.getUser(username)?.profileImageUri
    }

    suspend fun getAnimeById(animeId: Int): Anime? {
        return withContext(Dispatchers.IO) {
            if (NetworkUtils.hasNetwork(context)) {
                try {
                    val response = apiService.getAnimeDetails(animeId)
                    response.data
                } catch (e: Exception) {
                    e.printStackTrace()
                    null
                }
            } else {
                null
            }
        }
    }
}