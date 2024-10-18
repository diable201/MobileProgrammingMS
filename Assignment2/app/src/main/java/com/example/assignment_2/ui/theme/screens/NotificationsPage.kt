package com.example.assignment_2.ui.theme.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.assignment_2.ui.theme.components.NotificationItem
import com.example.assignment_2.viewmodel.MainViewModel

@Composable
fun NotificationsPage(navController: NavController, mainViewModel: MainViewModel) {
    // Observe notifications from the ViewModel
    val notifications by mainViewModel.notifications.observeAsState(initial = emptyList())

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(notifications, key = { it.id }) { notification ->
            NotificationItem(notification = notification, navController = navController)
        }
    }
}