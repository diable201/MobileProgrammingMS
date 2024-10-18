package com.example.assignment_2.models

import android.graphics.Bitmap

data class Post(
    val id: Int,
    val user: User,
    val imageUrl: String? = null,
    val imageData: Bitmap? = null,
    val caption: String,
    var likes: Int,
    var isLiked: Boolean = false,
    var comments: List<Comment> = emptyList()
) {
    override fun toString(): String {
        return "Post(id=$id, caption='$caption')"
    }
}