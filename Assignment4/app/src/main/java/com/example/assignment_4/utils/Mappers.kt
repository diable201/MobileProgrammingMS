package com.example.assignment_4.utils

import com.example.assignment_4.model.Anime
import com.example.assignment_4.model.CachedTopAnime
import com.example.assignment_4.model.CachedUpcomingAnime


fun Anime.toCachedTopAnime(currentTime: Long): CachedTopAnime {
    return CachedTopAnime(
        mal_id = this.mal_id,
        title = this.title,
        synopsis = this.synopsis,
        image_url = this.images.jpg.image_url,
        episodes = this.episodes,
        score = this.score,
        cachedAt = currentTime
    )
}

fun CachedTopAnime.toAnime(): Anime {
    return Anime(
        mal_id = this.mal_id,
        title = this.title,
        synopsis = this.synopsis,
        images = com.example.assignment_4.model.Images(
            jpg = com.example.assignment_4.model.ImageUrl(image_url)
        ),
        episodes = this.episodes,
        score = this.score
    )
}

fun Anime.toCachedUpcomingAnime(currentTime: Long): CachedUpcomingAnime {
    return CachedUpcomingAnime(
        mal_id = this.mal_id,
        title = this.title,
        synopsis = this.synopsis,
        image_url = this.images.jpg.image_url,
        episodes = this.episodes,
        score = this.score,
        cachedAt = currentTime
    )
}

fun CachedUpcomingAnime.toAnime(): Anime {
    return Anime(
        mal_id = this.mal_id,
        title = this.title,
        synopsis = this.synopsis,
        images = com.example.assignment_4.model.Images(
            jpg = com.example.assignment_4.model.ImageUrl(image_url)
        ),
        episodes = this.episodes,
        score = this.score
    )
}