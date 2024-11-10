package com.example.assignment_3.ui.theme.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {
    var inputText = mutableStateOf("")
        private set

    fun updateText(newText: String) {
        inputText.value = newText
    }
}