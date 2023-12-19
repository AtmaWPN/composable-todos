package com.example.testapplication.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.testapplication.data.TodoList
import com.example.testapplication.data.todos.Todo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateTodo(onSave: (Todo) -> Unit, setDefaultList: (TodoList) -> Unit, todoListID: Int, modifier: Modifier = Modifier) {
    val todoName = remember { mutableStateOf("") }
    Column(modifier = modifier) {
        TextField(value = todoName.value, onValueChange = { todoName.value = it })
        Button(onClick = {
            if (todoName.value == "") {
                return@Button
            }
            if (todoListID == -1) {
                println("trying to add a new todolist to the db")
                val newList = TodoList(name = "default list (in DB)")
                setDefaultList(newList)
                val todo = Todo(taskText = todoName.value, todoListID = 1, parentTodoID = null)
                onSave(todo)
                todoName.value = ""
            } else {
                println("adding todo to list id: $todoListID")
                val todo = Todo(taskText = todoName.value, todoListID = todoListID, parentTodoID = null)
                onSave(todo)
                todoName.value = ""
            }
        }) {
            Text(text = "Save")
        }
    }
}