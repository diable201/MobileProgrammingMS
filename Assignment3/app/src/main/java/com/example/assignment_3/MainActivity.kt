package com.example.assignment_3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.assignment_3.ui.theme.Assignment3Theme
import com.example.assignment_3.ui.theme.navigation.NavGraph

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Assignment3Theme {
                NavGraph()
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Assignment3Theme {
    }
}