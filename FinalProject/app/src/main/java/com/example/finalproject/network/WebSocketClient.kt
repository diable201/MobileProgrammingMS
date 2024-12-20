package com.example.finalproject.network

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WebSocketClient @Inject constructor(
    private val coroutineScope: CoroutineScope
) {

    private val client = OkHttpClient()
    private var webSocket: WebSocket? = null

    private val _incomingMessages = MutableSharedFlow<String>()
    val incomingMessages: SharedFlow<String> = _incomingMessages

    private val _connectionStatus = MutableSharedFlow<Boolean>()
    val connectionStatus: SharedFlow<Boolean> = _connectionStatus

    fun connect(url: String) {
        val request = Request.Builder().url(url).build()

        webSocket = client.newWebSocket(request, object : WebSocketListener() {

            override fun onOpen(ws: WebSocket, response: Response) {
                Log.d("WebSocketManager", "WebSocket connection opened")
                coroutineScope.launch {
                    _connectionStatus.emit(true)
                }
            }

            override fun onMessage(ws: WebSocket, text: String) {
                Log.d("WebSocketManager", "Received message: $text")
                coroutineScope.launch {
                    _incomingMessages.emit(text)
                }
            }

            override fun onMessage(ws: WebSocket, bytes: ByteString) {
                Log.d("WebSocketManager", "Received bytes: ${bytes.hex()}")
            }

            override fun onClosing(ws: WebSocket, code: Int, reason: String) {
                Log.d("WebSocketManager", "WebSocket closing: $code / $reason")
                ws.close(1000, null)
                coroutineScope.launch {
                    _connectionStatus.emit(false)
                }
            }

            override fun onFailure(ws: WebSocket, t: Throwable, response: Response?) {
                Log.e("WebSocketManager", "WebSocket error: ${t.message}")
                coroutineScope.launch {
                    _connectionStatus.emit(false)
                }
            }
        })
    }

    fun sendMessage(message: String) {
        webSocket?.send(message)
    }

    fun disconnect() {
        webSocket?.close(1000, "Client disconnecting")
        webSocket = null
    }
}