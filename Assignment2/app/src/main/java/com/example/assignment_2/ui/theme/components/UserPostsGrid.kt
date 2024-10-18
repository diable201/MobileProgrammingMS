package com.example.assignment_2.ui.theme.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.assignment_2.models.Post
import com.example.assignment_2.navigation.Screen

@Composable
fun UserPostsGrid(posts: List<Post>, navController: NavController) {
    val gridState = rememberLazyGridState()
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        state = gridState,
        contentPadding = PaddingValues(1.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(posts) { post ->
            Image(
                painter = rememberAsyncImagePainter(model = post.imageUrl ?: post.imageData),
                contentDescription = "Post Image",
                modifier = Modifier
                    .aspectRatio(1f)
                    .clickable {
                        navController.navigate(Screen.PostDetails.createRoute(post.id))
                    }
            )
        }
    }
}