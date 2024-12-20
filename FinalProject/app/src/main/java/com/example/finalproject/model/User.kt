package com.example.finalproject.model


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey val userId: Int, val username: String, val email: String, val passwordHash: String

)