package com.example.assignment_2.models

data class Comment(
    val id: Int,
    val user: User,
    val text: String,
    val timestamp: String
)