package com.example.assignment_4.ui.theme.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.assignment_4.model.Anime

@Composable
fun AnimeList(
    navController: NavController,
    animeList: List<Anime>,
    favoriteAnimeIds: List<Int>,
    onFavoriteClick: (Anime) -> Unit
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        items(animeList) { anime ->
            AnimeItem(
                anime = anime,
                navController = navController,
                onFavoriteClick = onFavoriteClick,
                isFavorite = favoriteAnimeIds.contains(anime.mal_id)
            )
        }
    }
}