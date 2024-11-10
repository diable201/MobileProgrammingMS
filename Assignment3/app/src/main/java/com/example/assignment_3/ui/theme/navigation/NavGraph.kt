package com.example.assignment_3.ui.theme.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import com.example.assignment_3.ui.theme.screens.*

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "message_screen") {
        composable("message_screen") { MessageScreen(navController) }
        composable("input_screen") { InputScreen(navController) }
        composable("output_screen") { OutputScreen(navController) }
        composable("anime_list_screen") { AnimeListScreen(navController) }
        composable("user_list_screen") { UserListScreen(navController) }
    }
}