package com.example.assignment_2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.assignment_2.navigation.AppNavGraph
import com.example.assignment_2.ui.theme.Assignment2Theme
import com.example.assignment_2.ui.theme.components.BottomNavigationBar
import com.example.assignment_2.viewmodel.MainViewModel

class MainActivity : ComponentActivity() {
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Assignment2App(mainViewModel)
        }
    }
}

@Composable
fun Assignment2App(mainViewModel: MainViewModel) {
    Assignment2Theme {
        val navController = rememberNavController()
        Scaffold(
            bottomBar = { BottomNavigationBar(navController, mainViewModel) }
        ) { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                AppNavGraph(navController, mainViewModel)
            }
        }
    }
}