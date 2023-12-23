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
import com.example.testapplication.data.todolists.TodoList
import com.example.testapplication.data.todos.Todo
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateTodo(onSave: suspend (Todo) -> Unit,
               setDefaultList: suspend (TodoList) -> Unit,
               todoListID: Int,
               modifier: Modifier = Modifier) {

    val coroutineScope = rememberCoroutineScope()
    val todoName = remember { mutableStateOf("") }

    Column(modifier = modifier) {
        TextField(value = todoName.value, label = { Text(text = "Task Text") }, onValueChange = { todoName.value = it })
        Button(onClick = {
            if (todoName.value == "") {
                return@Button
            }
            val todo = if (todoListID == -1) {
                val newList = TodoList(name = "default list (in DB)")
                coroutineScope.launch { setDefaultList(newList) }
                Todo(taskText = todoName.value, todoListID = 1, parentTodoID = null)
            } else {
                Todo(taskText = todoName.value, todoListID = todoListID, parentTodoID = null)
            }
            coroutineScope.launch { onSave(todo) }
            todoName.value = ""
        }) {
            Text(text = "Save")
        }
    }
}