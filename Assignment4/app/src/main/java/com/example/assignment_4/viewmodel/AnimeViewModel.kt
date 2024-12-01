package com.example.assignment_4.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assignment_4.data.UserSession
import com.example.assignment_4.model.Anime
import com.example.assignment_4.model.FavoriteAnime
import com.example.assignment_4.repository.AnimeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnimeViewModel @Inject constructor(
    private val repository: AnimeRepository, private val userSession: UserSession
) : ViewModel() {

    private val _animeList = MutableStateFlow<List<Anime>>(emptyList())
    val animeList: StateFlow<List<Anime>> = _animeList.asStateFlow()

    private val _upcomingAnimeList = MutableStateFlow<List<Anime>>(emptyList())
    val upcomingAnimeList: StateFlow<List<Anime>> = _upcomingAnimeList.asStateFlow()

    val favoriteAnimeList: StateFlow<List<FavoriteAnime>> = repository.getAllFavorites().stateIn(
        scope = viewModelScope, started = SharingStarted.Lazily, initialValue = emptyList()
    )

    private val _profileImageUri = MutableStateFlow<String?>(null)
    val profileImageUri: StateFlow<String?> = _profileImageUri.asStateFlow()

    init {
        viewModelScope.launch {
            _animeList.value = repository.getTopAnime()
            _upcomingAnimeList.value = repository.getUpcomingAnime()
            _profileImageUri.value = repository.getUserProfileImage(userSession.currentUser!!)
        }
    }

    fun addFavorite(anime: Anime) {
        viewModelScope.launch {
            val favoriteAnime = FavoriteAnime(
                mal_id = anime.mal_id, title = anime.title, image_url = anime.images.jpg.image_url
            )
            repository.addFavorite(favoriteAnime)
        }
    }

    fun removeFavorite(anime: FavoriteAnime) {
        viewModelScope.launch {
            repository.removeFavorite(anime)
        }
    }

    fun updateProfileImage(uri: String?) {
        viewModelScope.launch {
            repository.updateUserProfileImage(userSession.currentUser!!, uri)
            _profileImageUri.value = uri
        }
    }

    fun logout() {
        userSession.currentUser = null
    }

    fun toggleFavorite(anime: Anime) {
        viewModelScope.launch {
            val isFavorite = favoriteAnimeList.value.any { it.mal_id == anime.mal_id }
            if (isFavorite) {
                val favoriteAnime = favoriteAnimeList.value.find { it.mal_id == anime.mal_id }
                if (favoriteAnime != null) {
                    repository.removeFavorite(favoriteAnime)
                }
            } else {
                val favoriteAnime = FavoriteAnime(
                    mal_id = anime.mal_id,
                    title = anime.title,
                    image_url = anime.images.jpg.image_url
                )
                repository.addFavorite(favoriteAnime)
            }
        }
    }

    fun getCurrentUsername(): String {
        return userSession.currentUser ?: "Guest"
    }

    suspend fun getAnimeById(animeId: Int): Anime? {
        return repository.getAnimeById(animeId)
    }
}