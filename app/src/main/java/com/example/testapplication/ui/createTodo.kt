package com.example.testapplication.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.example.testapplication.data.Todo
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateTodo(viewModel: TodoViewModel, modifier: Modifier = Modifier) {
    val todoName = remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()
    Column(modifier = modifier) {
        TextField(value = todoName.value, onValueChange = { todoName.value = it })
        Button(onClick = {
            val todo = Todo(taskText = todoName.value)
            coroutineScope.launch {
                viewModel.dataContainer.todosRepository.insertTodo(todo)
            }
            todoName.value = ""
        }) {
            Text(text = "Save")
        }
    }
}