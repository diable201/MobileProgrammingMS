package com.example.finalproject.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reviews")
data class Review(
    @PrimaryKey(autoGenerate = true) val reviewId: Int = 0,
    val productId: Int,
    val userId: Int,
    val rating: Int,
    val comment: String
)