package com.example.finalproject.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_addresses")
data class UserAddress(
    @PrimaryKey val addressId: Int,
    val userId: Int,
    val street: String,
    val city: String,
    val state: String,
    val zipCode: String
)