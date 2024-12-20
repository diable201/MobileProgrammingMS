package com.example.finalproject.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.model.Order
import com.example.finalproject.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CheckoutViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    fun processCheckout(
        userId: Int, totalAmount: Double, onSuccess: () -> Unit, onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val order = Order(
                    orderId = (System.currentTimeMillis() / 1000).toInt(),
                    userId = userId,
                    orderDate = "2024-12-17",
                    totalAmount = totalAmount,
                    status = "Processing"
                )
                repository.insertOrder(order)
                onSuccess()
            } catch (e: Exception) {
                onError(e.message ?: "Checkout failed")
            }
        }
    }
}