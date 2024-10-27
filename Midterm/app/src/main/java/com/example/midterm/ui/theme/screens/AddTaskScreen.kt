package com.example.midterm.ui.theme.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.midterm.data.Task
import com.example.midterm.viewmodel.AuthViewModel
import com.example.midterm.viewmodel.TaskViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskScreen(
    taskViewModel: TaskViewModel,
    authViewModel: AuthViewModel,
    onNavigateBack: () -> Unit
) {
    val currentUser by authViewModel.currentUser.observeAsState()
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Add Task") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp),
                    maxLines = 4
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        val newTask = Task(
                            username = currentUser?.username ?: "",
                            title = title,
                            description = description
                        )
                        taskViewModel.addTask(newTask)
                        onNavigateBack()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = title.isNotBlank() && description.isNotBlank()
                ) {
                    Text("Add Task")
                }
            }
        }
    )
}
