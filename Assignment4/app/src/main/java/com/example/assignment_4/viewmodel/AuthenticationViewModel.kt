package com.example.assignment_4.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assignment_4.data.UserSession
import com.example.assignment_4.model.User
import com.example.assignment_4.repository.AnimeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private val repository: AnimeRepository, private val userSession: UserSession
) : ViewModel() {

    private val _loginSuccess = MutableStateFlow(false)
    val loginSuccess: StateFlow<Boolean> = _loginSuccess.asStateFlow()

    fun loginUser(username: String, password: String) {
        viewModelScope.launch {
            val user = repository.getUser(username)
            if (user != null && user.password == password) {
                userSession.currentUser = username
                _loginSuccess.value = true
            } else {
                _loginSuccess.value = false
            }
        }
    }

    fun registerUser(username: String, password: String) {
        viewModelScope.launch {
            val user = User(username = username, password = password)
            repository.registerUser(user)
            userSession.currentUser = username
            _loginSuccess.value = true
        }
    }
}