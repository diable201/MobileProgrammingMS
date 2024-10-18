package com.example.assignment_2.ui.theme.components

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.assignment_2.navigation.Screen
import com.example.assignment_2.viewmodel.MainViewModel

@Composable
fun BottomNavigationBar(navController: NavController, mainViewModel: MainViewModel) {
    val items = listOf(
        Screen.Home,
        Screen.Search,
        Screen.AddPost,
        Screen.Notifications,
        Screen.Profile,
    )

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.primaryContainer
    ) {
        val navBackStackEntry = navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry.value?.destination?.route

        items.forEach { screen ->
            val isSelected = currentRoute?.startsWith(screen.route) == true
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = when (screen) {
                            Screen.Home -> Icons.Default.Home
                            Screen.Search -> Icons.Default.Search
                            Screen.AddPost -> Icons.Default.Add
                            Screen.Notifications -> Icons.Default.Notifications
                            Screen.Profile -> Icons.Filled.Person
                            else -> {
                                throw IllegalArgumentException("Invalid screen route: $screen")
                            }
                        },
                        contentDescription = screen.route
                    )
                },
                label = {
                    Text(
                        text = when (screen) {
                            Screen.Home -> "Home"
                            Screen.Search -> "Search"
                            Screen.AddPost -> "Add"
                            Screen.Notifications -> "Notifications"
                            Screen.Profile -> "Profile"
                            else -> {
                                throw IllegalArgumentException("Invalid screen route: $screen")
                            }
                        }
                    )
                },
                selected = isSelected,
                onClick = {
                    if (!isSelected) {
                        Log.d("BottomNavigationBar", "Navigating to ${screen.route}")
                        if (screen.route == "profile/{userId}") {
                            navController.navigate(Screen.Profile.createRoute(mainViewModel.currentUser.id)) {
                                popUpTo(navController.graph.id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                            }
                        } else {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                // Avoid multiple copies of the same destination
                                launchSingleTop = true
                                // Restore state when re-selecting a previously selected item
                                restoreState = true
                            }
                        }
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    unselectedIconColor = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.6f),
                    selectedTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    unselectedTextColor = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.6f),
                    indicatorColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    }
}
