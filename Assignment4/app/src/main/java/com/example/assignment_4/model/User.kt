package com.example.assignment_4.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey
    val username: String,
    @ColumnInfo(name = "password")
    val password: String,
    @ColumnInfo(name = "profile_image_uri")
    val profileImageUri: String? = null
)
