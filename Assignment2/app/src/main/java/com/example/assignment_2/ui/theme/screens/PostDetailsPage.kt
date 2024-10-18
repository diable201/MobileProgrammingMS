package com.example.assignment_2.ui.theme.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.assignment_2.ui.theme.components.CommentItem
import com.example.assignment_2.ui.theme.components.PostItem
import com.example.assignment_2.viewmodel.MainViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostDetailsPage(navController: NavController, postId: Int, mainViewModel: MainViewModel) {
    val posts by mainViewModel.posts.observeAsState()
    val post = posts?.find { it.id == postId }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Post Details") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        if (post != null) {
            Column(modifier = Modifier.padding(innerPadding)) {
                PostItem(
                    post = post,
                    navController = navController,
                    onLikeClicked = {
                        mainViewModel.toggleLike(post.id)
                    }
                )
                Text(
                    text = "Comments",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(8.dp)
                )
                LazyColumn(modifier = Modifier.weight(1f)) {
                    items(post.comments) { comment ->
                        CommentItem(comment)
                    }
                }
                var commentText by remember { mutableStateOf("") }
                Row(modifier = Modifier.padding(8.dp)) {
                    TextField(
                        value = commentText,
                        onValueChange = { commentText = it },
                        placeholder = { Text("Add a comment...") },
                        modifier = Modifier.weight(1f)
                    )
                    Button(
                        onClick = {
                            if (commentText.isNotBlank()) {
                                mainViewModel.addComment(post.id, commentText)
                                commentText = ""
                            }
                        },
                        modifier = Modifier.padding(start = 8.dp)
                    ) {
                        Text("Post")
                    }
                }
            }
        } else {
            Text("Post not found", modifier = Modifier.padding(16.dp))
        }
    }
}