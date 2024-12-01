package com.example.assignment_4.ui.theme.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.assignment_4.ui.theme.components.AnimeList
import com.example.assignment_4.viewmodel.AnimeViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController, animeViewModel: AnimeViewModel
) {
    val animeList by animeViewModel.animeList.collectAsState()
    val upcomingAnimeList by animeViewModel.upcomingAnimeList.collectAsState()
    val favoriteAnimeList by animeViewModel.favoriteAnimeList.collectAsState()
    val favoriteAnimeIds = favoriteAnimeList.map { it.mal_id }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Weebnet") }, actions = {
                IconButton(onClick = {
                    animeViewModel.logout()
                    navController.navigate("login") {
                        popUpTo("main") { inclusive = true }
                    }
                }) {
                    Icon(
                        Icons.AutoMirrored.Filled.ExitToApp, contentDescription = "Logout"
                    )
                }
            }, colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.background,
                titleContentColor = MaterialTheme.colorScheme.onBackground,
                navigationIconContentColor = MaterialTheme.colorScheme.onBackground,
                actionIconContentColor = MaterialTheme.colorScheme.onBackground
            )
            )
        }, containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(8.dp)
        ) {
            Text(
                "Trending Anime",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(8.dp)
            )
            AnimeList(navController = navController,
                animeList = animeList,
                favoriteAnimeIds = favoriteAnimeIds,
                onFavoriteClick = { anime ->
                    animeViewModel.toggleFavorite(anime)
                })

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                "Upcoming Anime",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(8.dp)
            )
            AnimeList(navController = navController,
                animeList = upcomingAnimeList,
                favoriteAnimeIds = favoriteAnimeIds,
                onFavoriteClick = { anime ->
                    animeViewModel.toggleFavorite(anime)
                })
        }
    }
}