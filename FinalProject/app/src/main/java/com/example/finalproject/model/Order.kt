package com.example.finalproject.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "orders")
data class Order(
    @PrimaryKey val orderId: Int,
    val userId: Int,
    val orderDate: String,
    val totalAmount: Double,
    val status: String
)


data class UserWithOrders(
    val user: User, val orders: List<Order>
)