package com.example.finalproject.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "payments")
data class Payment(
    @PrimaryKey val paymentId: Int,
    val orderId: Int,
    val amount: Double,
    val paymentDate: String,
    val paymentMethod: String
)