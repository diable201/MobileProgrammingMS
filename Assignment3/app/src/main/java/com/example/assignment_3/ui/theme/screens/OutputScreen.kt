package com.example.assignment_3.ui.theme.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.assignment_3.ui.theme.viewmodel.SharedViewModel

@Composable
fun OutputScreen(navController: NavController) {
    val parentEntry = remember { navController.getBackStackEntry("message_screen") }
    val sharedViewModel: SharedViewModel = viewModel(parentEntry)
    val text by sharedViewModel.inputText

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "You entered: $text")

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { navController.navigateUp() }) {
            Text("Go Back")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = {
            navController.navigate("message_screen") {
                popUpTo("message_screen") { inclusive = false }
            }
        }) {
            Text("Return to Home")
        }
    }
}