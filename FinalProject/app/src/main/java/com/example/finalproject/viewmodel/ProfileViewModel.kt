package com.example.finalproject.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.model.User
import com.example.finalproject.model.UserWithOrders
import com.example.finalproject.repository.OrderManager
import com.example.finalproject.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: Repository, private val orderManager: OrderManager
) : ViewModel() {

    private val _userWithOrders = MutableStateFlow<UserWithOrders?>(null)
    val userWithOrders: StateFlow<UserWithOrders?> = _userWithOrders.asStateFlow()

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user

    init {
        fetchUser()
    }

    private fun fetchUser() {
        viewModelScope.launch {
            try {
                val user = repository.getUserByEmail("diable@gmail.com")
                orderManager.orders.collect { orders ->
                    _userWithOrders.value = UserWithOrders(user, orders)
                }
            } catch (e: Exception) {
                Log.e("ProfileViewModel", "Error fetching user")
            }
        }
    }
}
