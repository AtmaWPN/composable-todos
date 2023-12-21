package com.example.testapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
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
                Column(modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Bottom) {
                    BottomNavigation {
                        val navBackStackEntry = navController.currentBackStackEntryAsState()
                        val currentDestination = navBackStackEntry.value?.destination
                        // loop through possibilities
                        val routes = listOf(
                            "home",
                            "list_manager"
                        )
                        routes.forEach { route ->
                            BottomNavigationItem(
                                icon = { Icon(Icons.Filled.List, contentDescription = null) },
                                label = { Text(text = route) },
                                selected = currentDestination?.hierarchy?.any { it.route == route } == true,
                                onClick = {
                                    navController.navigate(route) {
                                        // Pop up to the start destination of the graph to
                                        // avoid building up a large stack of destinations
                                        // on the back stack as users select items
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        // Avoid multiple copies of the same destination when
                                        // reselecting the same item
                                        launchSingleTop = true
                                        // Restore state when reselecting a previously selected item
                                        restoreState = true
                                    }
                                }
                            )
                        }
                    }
                }
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