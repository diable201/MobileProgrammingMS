package com.example.assignment_4.ui.theme.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavigationDestination(val route: String, val icon: ImageVector, val label: String) {
    data object Home : NavigationDestination("home", Icons.Default.Home, "Home")
    data object Profile : NavigationDestination("profile", Icons.Default.Person, "Profile")
}