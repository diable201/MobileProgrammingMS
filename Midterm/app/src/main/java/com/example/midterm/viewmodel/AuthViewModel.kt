package com.example.midterm.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.midterm.data.AppDatabase
import com.example.midterm.data.User
import kotlinx.coroutines.launch

class AuthViewModel(application: Application) : AndroidViewModel(application) {
    private val userDao = AppDatabase.getDatabase(application).userDao()

    private val _isAuthenticated = MutableLiveData<Boolean>(false)
    val isAuthenticated: LiveData<Boolean> get() = _isAuthenticated

    private val _currentUser = MutableLiveData<User?>()
    val currentUser: LiveData<User?> get() = _currentUser

    fun login(username: String, password: String) = viewModelScope.launch {
        val user = userDao.getUserByUsername(username)
        if (user != null && user.password == password) {
            _isAuthenticated.postValue(true)
            _currentUser.postValue(user)
        } else {
            _isAuthenticated.postValue(false)
        }
    }

    fun logout() {
        _isAuthenticated.value = false
        _currentUser.value = null
    }

    fun register(user: User) = viewModelScope.launch {
        userDao.insertUser(user)
        _isAuthenticated.postValue(true)
        _currentUser.postValue(user)
    }

    fun updateProfileImage(uri: String) = viewModelScope.launch {
        val user = _currentUser.value
        if (user != null) {
            val updatedUser = user.copy(profileImageUri = uri)
            userDao.updateUser(updatedUser)
            _currentUser.postValue(updatedUser)
        }
    }
}