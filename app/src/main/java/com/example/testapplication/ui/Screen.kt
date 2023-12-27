package com.example.testapplication.ui

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Menu
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.testapplication.R

sealed class Screen(val route: String, @StringRes val resourceId: Int, val icon: ImageVector) {
    data object Home : Screen("home", R.string.home_todos_label, Icons.Filled.List)
    data object ListManager : Screen("list_manager", R.string.list_manager_label, Icons.Filled.Menu)
}