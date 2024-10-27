package com.example.midterm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.midterm.ui.theme.MainApp
import com.example.midterm.ui.theme.MidtermTheme
import com.example.midterm.viewmodel.AuthViewModel
import com.example.midterm.viewmodel.TaskViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val taskViewModel: TaskViewModel = viewModel()
            val authViewModel: AuthViewModel = viewModel()
            MidtermTheme {
                MainApp(taskViewModel)
            }
        }
    }
}