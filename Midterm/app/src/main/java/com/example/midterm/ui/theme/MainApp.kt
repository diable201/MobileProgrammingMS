package com.example.midterm.ui.theme

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.midterm.ui.theme.components.BottomNavBar
import com.example.midterm.ui.theme.screens.AddTaskScreen
import com.example.midterm.ui.theme.screens.LoginScreen
import com.example.midterm.ui.theme.screens.ProfileScreen
import com.example.midterm.ui.theme.screens.TaskDetailScreen
import com.example.midterm.ui.theme.screens.TaskListScreen
import com.example.midterm.ui.theme.screens.TaskSummaryScreen
import com.example.midterm.viewmodel.AuthViewModel
import com.example.midterm.viewmodel.TaskViewModel


@Composable
fun MainApp(
    taskViewModel: TaskViewModel,
    authViewModel: AuthViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val navController = rememberNavController()
    val isAuthenticated by authViewModel.isAuthenticated.observeAsState(false)
    val currentUser by authViewModel.currentUser.observeAsState(null)

    if (!isAuthenticated) {
        LoginScreen(authViewModel = authViewModel) {
            currentUser?.username?.let { username ->
                taskViewModel.loadTasksForUser(username)
            }
        }
    } else {
        currentUser?.username?.let { username ->
            taskViewModel.loadTasksForUser(username)
        }
        MainContent(navController = navController, taskViewModel = taskViewModel, authViewModel = authViewModel)
    }
}

@Composable
fun MainContent(
    navController: NavHostController,
    taskViewModel: TaskViewModel,
    authViewModel: AuthViewModel
) {
    Scaffold(
        bottomBar = {
            BottomNavBar(navController)
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "taskList",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("taskList") {
                TaskListScreen(
                    taskViewModel = taskViewModel,
                    authViewModel = authViewModel,
                    onAddTask = { navController.navigate("addTask") },
                    onTaskClick = { taskId ->
                        navController.navigate("taskDetail/$taskId")
                    },
                    onViewSummary = { navController.navigate("taskSummary") },
                )
            }
            composable("taskSummary") {
                TaskSummaryScreen(
                    taskViewModel = taskViewModel,
                    onNavigateBack = { navController.popBackStack() }
                )
            }
            composable("addTask") {
                AddTaskScreen(
                    taskViewModel = taskViewModel,
                    authViewModel = authViewModel,
                    onNavigateBack = { navController.popBackStack() }
                )
            }
            composable(
                route = "taskDetail/{taskId}",
                arguments = listOf(navArgument("taskId") { type = NavType.IntType })
            ) { backStackEntry ->
                val taskId = backStackEntry.arguments?.getInt("taskId") ?: 0
                TaskDetailScreen(
                    taskId = taskId,
                    taskViewModel = taskViewModel,
                    authViewModel = authViewModel,
                    onNavigateBack = { navController.popBackStack() }
                )
            }
            composable("profile") {
                ProfileScreen(authViewModel = authViewModel)
            }
        }
    }
}