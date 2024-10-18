package com.example.assignment_2.ui.theme.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.assignment_2.models.Post
import com.example.assignment_2.navigation.Screen

@Composable
fun PostItem(
    post: Post,
    navController: NavController,
    onLikeClicked: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .clickable {
                    navController.navigate(Screen.Profile.createRoute(post.user.id)) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }, verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberAsyncImagePainter(model = post.user.profileImageUrl),
                contentDescription = "Profile Image",
                modifier = Modifier.size(40.dp).clip(CircleShape)
            )
            Text(
                text = post.user.username,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(start = 8.dp)
                    .clickable {
                        navController.navigate(Screen.Profile.createRoute(post.user.id)) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
            )
        }
        when {
            post.imageData != null -> {
                Image(
                    bitmap = post.imageData.asImageBitmap(),
                    contentDescription = "Post Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                )
            }

            post.imageUrl != null -> {
                Image(
                    painter = rememberAsyncImagePainter(model = post.imageUrl),
                    contentDescription = "Post Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                )
            }

            else -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .background(Color.Gray),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No Image", color = Color.White)
                }
            }
        }
        Row(modifier = Modifier.padding(8.dp)) {
            IconButton(onClick = onLikeClicked) {
                Icon(
                    imageVector = if (post.isLiked) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                    contentDescription = "Like"
                )
            }
            IconButton(onClick = {
                navController.navigate(Screen.PostDetails.createRoute(post.id))
            }) {
                Icon(imageVector = Icons.Outlined.ChatBubbleOutline, contentDescription = "Comment")
            }
        }
        Text(
            text = "${post.likes} likes", modifier = Modifier.padding(horizontal = 8.dp)
        )
        Text(
            text = "${post.user.username} ${post.caption}",
            modifier = Modifier.
                padding(horizontal = 8.dp)
                .clickable {
                    navController.navigate(Screen.Profile.createRoute(post.user.id)) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
        )
    }

}