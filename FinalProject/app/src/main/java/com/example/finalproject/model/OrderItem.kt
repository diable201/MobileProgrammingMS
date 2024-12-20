package com.example.finalproject.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "order_items")
data class OrderItem(
    @PrimaryKey val orderItemId: Int,
    val orderId: Int,
    val productId: Int,
    val quantity: Int,
    val price: Double
)