package com.example.assignment_3.ui.theme.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.assignment_3.ui.theme.viewmodel.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserListScreen(navController: NavController, userViewModel: UserViewModel = viewModel()) {
    val users by userViewModel.userList.collectAsState()
    val newName = userViewModel.newName

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("User List") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .padding(16.dp)) {

            TextField(
                value = newName.value,
                onValueChange = { newName.value = it },
                label = { Text("Enter user name") }
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = { userViewModel.addUser() }) {
                Text("Add User")
            }
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn {
                items(users) { user ->
                    Text(text = "${user.name}, Age: ${user.age}",
                        modifier = Modifier.padding(8.dp))
                    HorizontalDivider()
                }
            }
        }
    }
}