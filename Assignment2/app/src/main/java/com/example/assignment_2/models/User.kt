package com.example.assignment_2.models

data class User(
    val id: Int,
    val username: String,
    val profileImageUrl: String,
    val bio: String? = null,
    var postsCount: Int? = null,
    var posts: List<Post>? = null
) {
    override fun toString(): String {
        return "User(username='$username')"
    }
}