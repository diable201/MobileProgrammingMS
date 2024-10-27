package com.example.midterm.ui.theme.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.midterm.viewmodel.TaskViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskSummaryScreen(
    taskViewModel: TaskViewModel,
    onNavigateBack: () -> Unit
) {
    val tasks by taskViewModel.tasks.observeAsState(emptyList())

    val sortedIncompleteTaskTitles = tasks
        .filter { !it.isCompleted }
        .sortedBy { it.title }
        .map { it.title }

    val sortedCompletedTaskTitles = tasks
        .filter { it.isCompleted }
        .sortedBy { it.title }
        .map { it.title }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Task Summary") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Text(
                text = "You have ${tasks.size} tasks:",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            HorizontalDivider()
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Incomplete Tasks",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            LazyColumn {
                items(sortedIncompleteTaskTitles) { title ->
                    TaskTitleItem(title = title, isCompleted = false)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Completed Tasks",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            LazyColumn {
                items(sortedCompletedTaskTitles) { title ->
                    TaskTitleItem(title = title, isCompleted = true)
                }
            }
        }
    }
}

@Composable
fun TaskTitleItem(title: String, isCompleted: Boolean) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        Icon(
            imageVector = if (isCompleted) Icons.Default.CheckCircle else Icons.Default.RadioButtonUnchecked,
            contentDescription = null,
            tint = if (isCompleted) Color.Green else Color.Gray
        )
        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
        )
    }
}
