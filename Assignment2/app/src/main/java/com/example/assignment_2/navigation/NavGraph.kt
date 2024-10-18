package com.example.assignment_2.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.assignment_2.ui.theme.screens.HomeFeedPage
import com.example.assignment_2.ui.theme.screens.AddPostPage
import com.example.assignment_2.ui.theme.screens.NotificationsPage
import com.example.assignment_2.ui.theme.screens.PostDetailsPage
import com.example.assignment_2.ui.theme.screens.ProfilePage
import com.example.assignment_2.ui.theme.screens.SearchPage
import com.example.assignment_2.viewmodel.MainViewModel

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object Search : Screen("search")
    data object AddPost : Screen("add_post")
    data object Notifications : Screen("notifications")
    data object Profile : Screen("profile/{userId}") {
        fun createRoute(userId: Int) = "profile/$userId"
    }
    data object PostDetails : Screen("post_details/{postId}") {
        fun createRoute(postId: Int) = "post_details/$postId"
    }
}

@Composable
fun AppNavGraph(navController: NavHostController, mainViewModel: MainViewModel) {
    NavHost(navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) {
            HomeFeedPage(navController, mainViewModel)
        }
        composable(Screen.Search.route) {
            SearchPage(navController, mainViewModel)
        }
        composable(Screen.AddPost.route) {
            AddPostPage(navController, mainViewModel)
        }
        composable(Screen.Notifications.route) {
            NotificationsPage(navController, mainViewModel)
        }
        composable(
            route = Screen.Profile.route,
            arguments = listOf(navArgument("userId") { type = NavType.IntType })
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getInt("userId") ?: mainViewModel.currentUser.id
            ProfilePage(navController, mainViewModel, userId)
        }
        composable(
            route = Screen.PostDetails.route,
            arguments = listOf(navArgument("postId") { type = NavType.IntType })
        ) { backStackEntry ->
            val postId = backStackEntry.arguments?.getInt("postId") ?: 0
            PostDetailsPage(navController, postId, mainViewModel)
        }
    }
}
