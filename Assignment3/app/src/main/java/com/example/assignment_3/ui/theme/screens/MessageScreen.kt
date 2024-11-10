package com.example.assignment_3.ui.theme.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun MessageScreen(navController: NavController) {
    val tag = "MessageScreen"

    LaunchedEffect(Unit) {
        Log.d(tag, "Composable Entered Composition")
    }

    DisposableEffect(Unit) {
        onDispose {
            Log.d(tag, "Composable Left Composition")
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Hello from Compose!")

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { navController.navigate("input_screen") }) {
            Text("Go to Input Screen")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = { navController.navigate("anime_list_screen") }) {
            Text("Go to Anime List Screen")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = { navController.navigate("user_list_screen") }) {
            Text("Go to User List Screen")
        }
    }
}