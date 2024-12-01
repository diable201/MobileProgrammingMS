package com.example.assignment_4.ui.theme.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.assignment_4.ui.theme.components.BottomNavigationBar
import com.example.assignment_4.ui.theme.navigation.NavigationDestination
import com.example.assignment_4.viewmodel.AnimeViewModel

@Composable
fun MainScreen(
    rootNavController: NavController, animeViewModel: AnimeViewModel = hiltViewModel()
) {
    val navController = rememberNavController()
    val items = listOf(
        NavigationDestination.Home, NavigationDestination.Profile
    )

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = navController, items = items)
        }, containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = NavigationDestination.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(NavigationDestination.Home.route) {
                HomeScreen(navController = rootNavController, animeViewModel)
            }
            composable(NavigationDestination.Profile.route) {
                ProfileScreen(navController = rootNavController, animeViewModel)
            }
        }
    }
}