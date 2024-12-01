package com.example.assignment_4.ui.theme.components

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.assignment_4.ui.theme.DarkGray
import com.example.assignment_4.ui.theme.LightGray
import com.example.assignment_4.ui.theme.NetflixRed
import com.example.assignment_4.ui.theme.navigation.NavigationDestination

@Composable
fun BottomNavigationBar(
    navController: NavController, items: List<NavigationDestination>
) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

        items.forEach { destination ->
            NavigationBarItem(icon = {
                Icon(
                    destination.icon,
                    contentDescription = destination.label
                )
            },
                label = { Text(destination.label) },
                selected = currentRoute == destination.route,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = NetflixRed,
                    unselectedIconColor = LightGray,
                    selectedTextColor = NetflixRed,
                    unselectedTextColor = LightGray,
                    indicatorColor = DarkGray
                ),
                onClick = {
                    navController.navigate(destination.route) {
                        popUpTo(navController.graph.startDestinationRoute ?: "") {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                })
        }
    }
}