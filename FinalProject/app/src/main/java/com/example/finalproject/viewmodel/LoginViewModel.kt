package com.example.finalproject.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _loginState = MutableStateFlow<Result<Boolean>>(Result.success(false))
    val loginState: StateFlow<Result<Boolean>> = _loginState

    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                val user = repository.getUserByEmail(email)
                if (user.passwordHash == password) {
                    _loginState.value = Result.success(true)
                } else {
                    _loginState.value = Result.failure(Exception("Invalid credentials"))
                }
            } catch (e: Exception) {
                _loginState.value = Result.failure(e)
            }
        }
    }
}