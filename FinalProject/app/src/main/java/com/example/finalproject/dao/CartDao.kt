package com.example.finalproject.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.finalproject.model.CartItem

@Dao
interface CartDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCartItem(cartItem: CartItem)

    @Query("SELECT * FROM cart_items WHERE cartId = :cartId")
    suspend fun getCartItems(cartId: Int): List<CartItem>

    @Delete
    suspend fun deleteCartItem(cartItem: CartItem)

    @Update
    suspend fun updateCartItem(cartItem: CartItem)
}