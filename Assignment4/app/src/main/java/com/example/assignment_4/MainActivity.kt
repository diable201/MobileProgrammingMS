package com.example.assignment_4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.assignment_4.ui.theme.Assignment4Theme
import com.example.assignment_4.ui.theme.navigation.NavGraph
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Assignment4Theme {
                val navController = rememberNavController()
                NavGraph(navController = navController)
            }
        }
    }
}