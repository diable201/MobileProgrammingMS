package com.example.assignment_3.ui.theme.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.assignment_3.ui.theme.viewmodel.SharedViewModel

@Composable
fun InputScreen(
    navController: NavController,
) {
    val parentEntry = remember(navController) {
        navController.getBackStackEntry("message_screen")
    }
    val sharedViewModel: SharedViewModel = viewModel(parentEntry)
    val text by sharedViewModel.inputText

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = text,
            onValueChange = { sharedViewModel.updateText(it) },
            label = { Text("Enter Text") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { navController.navigate("output_screen") }) {
            Text("Go to Output Screen")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = { navController.navigateUp() }) {
            Text("Go Back")
        }
    }
}