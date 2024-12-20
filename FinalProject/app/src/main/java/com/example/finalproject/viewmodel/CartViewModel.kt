package com.example.finalproject.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.model.CartItem
import com.example.finalproject.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val cartId = 1

    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> = _cartItems.asStateFlow()

    init {
        fetchCartItems()
    }

    private fun fetchCartItems() {
        viewModelScope.launch {
            _cartItems.value = repository.getCartItems(cartId)
        }
    }

    fun addToCart(productId: Int, quantity: Int = 1) {
        viewModelScope.launch {
            try {
                val existingItem = repository.getCartItemByProductId(cartId, productId)
                if (existingItem != null) {
                    val updatedItem = existingItem.copy(quantity = existingItem.quantity + quantity)
                    repository.updateCartItem(updatedItem)
                } else {
                    val newItem = CartItem(
                        cartId = cartId, productId = productId, quantity = quantity
                    )
                    repository.insertCartItem(newItem)
                }
                fetchCartItems()
            } catch (e: Exception) {
                Log.e("CartViewModel", "error")
            }
        }
    }

    fun removeFromCart(cartItem: CartItem) {
        viewModelScope.launch {
            try {
                repository.deleteCartItem(cartItem)
                fetchCartItems()
            } catch (e: Exception) {
                Log.d("CartViewModel", "error")
            }
        }
    }

    fun updateQuantity(cartItem: CartItem, newQuantity: Int) {
        viewModelScope.launch {
            try {
                if (newQuantity <= 0) {
                    repository.deleteCartItem(cartItem)
                } else {
                    val updatedItem = cartItem.copy(quantity = newQuantity)
                    repository.updateCartItem(updatedItem)
                }
                fetchCartItems()
            } catch (e: Exception) {
                Log.e("CartViewModel", "error")
            }
        }
    }

    fun clearCart() {
        viewModelScope.launch {
            try {
                repository.clearCart(cartId)
                fetchCartItems()
            } catch (e: Exception) {
                Log.e("CartViewModel", "error")
            }
        }
    }
}