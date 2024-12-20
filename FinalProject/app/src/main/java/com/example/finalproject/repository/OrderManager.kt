package com.example.finalproject.repository

import android.util.Log
import com.example.finalproject.model.Order
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OrderManager @Inject constructor() {
    private val _orders = MutableStateFlow<List<Order>>(emptyList())
    val orders: StateFlow<List<Order>> = _orders

    fun addOrder(order: Order) {
        _orders.value += order
        Log.d("OrderManager", "Order added: $order")
    }

    fun getOrders(): List<Order> {
        return _orders.value
    }
}