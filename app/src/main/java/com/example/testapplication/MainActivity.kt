package com.example.testapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.testapplication.ui.BottomNavBar
import com.example.testapplication.ui.ListManager
import com.example.testapplication.ui.MainTodos
import com.example.testapplication.ui.TodoViewModel
import com.example.testapplication.ui.theme.TestApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mainViewModel: TodoViewModel by viewModels()

        setContent {
            TestApplicationTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "home") {
                    composable("home") { MainTodos(mainViewModel = mainViewModel) }
                    composable("list_manager") { ListManager(viewModel = mainViewModel) }
                }
                BottomNavBar(navController = navController)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TestApplicationTheme {
        NavHost(navController = rememberNavController(), startDestination = "home") {
//            composable("home") { MainTodos() }
//            composable("home/createTodo") { CreateTodo() }
            /*...*/
        }
    }
}