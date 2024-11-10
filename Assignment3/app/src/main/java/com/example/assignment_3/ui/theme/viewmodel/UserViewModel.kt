package com.example.assignment_3.ui.theme.viewmodel

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.assignment_3.data.AppDatabase
import com.example.assignment_3.data.User
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.SharingStarted

class UserViewModel(application: Application) : AndroidViewModel(application) {
    private val db = Room.databaseBuilder(
        application,
        AppDatabase::class.java, "app_database"
    ).build()


    val userList: StateFlow<List<User>> = db.userDao()
        .getAllUsers()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    var newName = mutableStateOf("")

    fun addUser() {
        val name = newName.value
        if (name.isNotBlank()) {
            val newUser = User(name = name, age = (20..40).random())
            viewModelScope.launch {
                db.userDao().insert(newUser)
                newName.value = ""
            }
        }
    }
}