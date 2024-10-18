package com.example.assignment_2.ui.theme.screens

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.assignment_2.viewmodel.MainViewModel
import com.example.assignment_2.ui.theme.components.PostItem


@Composable
fun HomeFeedPage(navController: NavController, mainViewModel: MainViewModel) {
    val posts by mainViewModel.posts.observeAsState(emptyList())

    for (post in posts) {
        Log.d("HomeFeedPage", "Post ID: ${post.id}, Title: ${post.caption}")
    }

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(posts) { post ->
            PostItem(
                post = post,
                navController = navController,
                onLikeClicked = {
                    mainViewModel.toggleLike(post.id)
                }
            )
        }
    }
}