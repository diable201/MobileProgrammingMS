package com.example.assignment_4.ui.theme.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.assignment_4.ui.theme.screens.AnimeDetailScreen
import com.example.assignment_4.ui.theme.screens.LoginScreen
import com.example.assignment_4.ui.theme.screens.MainScreen
import com.example.assignment_4.ui.theme.screens.RegisterScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController, startDestination = "login") {
        composable("login") {
            LoginScreen(navController)
        }
        composable("register") {
            RegisterScreen(navController)
        }
        composable("main") {
            MainScreen(rootNavController = navController)
        }
        composable(
            "animeDetail/{animeId}",
            arguments = listOf(navArgument("animeId") { type = NavType.IntType })
        ) { backStackEntry ->
            val animeId = backStackEntry.arguments?.getInt("animeId")
            if (animeId != null) {
                Log.v("Nav message", "Anime ID: $animeId")
                AnimeDetailScreen(navController, animeId)
            }
        }
    }
}