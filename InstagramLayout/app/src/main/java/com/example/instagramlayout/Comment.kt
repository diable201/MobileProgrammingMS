package com.example.instagramlayout

data class Comment(
    val username: String,
    val message: String,
    val reply: String? = null
)

