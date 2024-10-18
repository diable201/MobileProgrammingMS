package com.example.assignment_2.models

data class NotificationItem(
    val id: Int,
    val user: User,
    val message: String,
    val timestamp: String,
    val targetId: Int? = null
)