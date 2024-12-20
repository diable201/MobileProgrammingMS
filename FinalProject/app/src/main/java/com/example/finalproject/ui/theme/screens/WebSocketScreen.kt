package com.example.finalproject.ui.theme.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.finalproject.viewmodel.WebSocketViewModel

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun WebSocketScreen(
    navController: NavController, webSocketViewModel: WebSocketViewModel = hiltViewModel()
) {
    val coroutineScope = rememberCoroutineScope()
    val incomingMessages by webSocketViewModel.messages.collectAsState()
    val isConnected by webSocketViewModel.isConnected.collectAsState()

    Scaffold(topBar = {
        TopAppBar(title = { Text("WebSocket") })
    }, content = { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (isConnected) {
                Text(
                    "Connected to WebSocket Server", color = MaterialTheme.colorScheme.primary
                )
            } else {
                Text(
                    "Disconnected", color = MaterialTheme.colorScheme.error
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            var currentMessage by remember { mutableStateOf("") }

            TextField(
                value = currentMessage,
                onValueChange = { currentMessage = it },
                label = { Text("Enter message") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    webSocketViewModel.sendMessage(currentMessage)
                    currentMessage = ""
                },
                enabled = isConnected && currentMessage.isNotBlank(),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Send")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text("Incoming Messages:", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                items(incomingMessages) { message ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.surfaceVariant)
                            .padding(8.dp)
                    ) {
                        Text(text = message)
                    }
                    HorizontalDivider()
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    webSocketViewModel.disconnect()
                }, enabled = isConnected, modifier = Modifier.fillMaxWidth()
            ) {
                Text("Disconnect")
            }
        }
    })
}