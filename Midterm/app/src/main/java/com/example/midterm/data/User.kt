package com.example.midterm.data


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey val username: String,
    val password: String,
    val fullName: String,
    val profileImageUri: String? = null
)