package com.example.assignment_2.ui.theme.screens

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.assignment_2.ui.theme.components.UserInfoSection
import com.example.assignment_2.ui.theme.components.UserPostsGrid
import com.example.assignment_2.viewmodel.MainViewModel

@Composable
fun ProfilePage(navController: NavController, mainViewModel: MainViewModel, userId: Int) {

    val user by mainViewModel.getUserById(userId)
        .observeAsState(initial = mainViewModel.currentUser)

    Log.d("ProfilePage", "User ID: $userId")

    val posts by mainViewModel.getPostsForUser(userId).observeAsState(initial = emptyList())
    val postCount by mainViewModel.getPostCountForUser(userId).observeAsState(initial = 0)
    user.postsCount = postCount

    Column(modifier = Modifier.fillMaxSize()) {
        UserInfoSection(user)
        UserPostsGrid(posts = posts, navController = navController)
    }
}