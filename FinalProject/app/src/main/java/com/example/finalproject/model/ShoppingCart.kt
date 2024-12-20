package com.example.finalproject.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shopping_cart")
data class ShoppingCart(
    @PrimaryKey val cartId: Int, val userId: Int, val createdAt: String
)