package com.example.finalproject.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.network.WebSocketClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WebSocketViewModel @Inject constructor(
    private val webSocketClient: WebSocketClient
) : ViewModel() {

    private val _messages = MutableStateFlow<List<String>>(emptyList())
    val messages: StateFlow<List<String>> = _messages

    private val _isConnected = MutableStateFlow(false)
    val isConnected: StateFlow<Boolean> = _isConnected

    init {
        startWebSocket()
    }

    private fun startWebSocket() {
        viewModelScope.launch {
            webSocketClient.connect("ws://10.0.2.2:8000/ws")
        }

        viewModelScope.launch {
            webSocketClient.incomingMessages.collect { message ->
                _messages.value += message
            }
        }

        viewModelScope.launch {
            webSocketClient.connectionStatus.collect { status ->
                _isConnected.value = status
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.launch {
            webSocketClient.disconnect()
        }
    }

    fun sendMessage(message: String) {
        viewModelScope.launch {
            webSocketClient.sendMessage(message)
        }
    }

    fun disconnect() {
        viewModelScope.launch {
            webSocketClient.disconnect()
        }
    }
}