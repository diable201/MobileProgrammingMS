package com.example.assignment_2.ui.theme.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.assignment_2.models.Comment

@Composable
fun CommentItem(comment: Comment) {
    Row(modifier = Modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = rememberAsyncImagePainter(model = comment.user.profileImageUrl),
            contentDescription = "Profile Image",
            modifier = Modifier
                .size(30.dp)
                .clip(CircleShape)
        )
        Column(modifier = Modifier.padding(start = 8.dp)) {
            Text(
                text = comment.user.username,
                fontWeight = FontWeight.Bold
            )
            Text(text = comment.text)
            Text(
                text = comment.timestamp,
                style = MaterialTheme.typography.labelSmall
            )
        }
    }
}