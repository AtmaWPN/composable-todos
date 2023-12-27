package com.example.testapplication.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import com.example.testapplication.R
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
    Surface(border = BorderStroke(Dp.Hairline, MaterialTheme.colorScheme.onSurface)) {
        Column(modifier = modifier) {
            TextField(
                value = todoName.value,
                label = { Text(text = stringResource(R.string.task_input_label)) },
                onValueChange = { todoName.value = it })
            val newListName = stringResource(R.string.default_list_name)
            Button(onClick = {
                if (todoName.value == "") {
                    return@Button
                }
                val todo = if (todoListID == -1) {
                    val newList = TodoList(name = newListName)
                    coroutineScope.launch { setDefaultList(newList) }
                    Todo(taskText = todoName.value.trim(), todoListID = 1, parentTodoID = null)
                } else {
                    Todo(taskText = todoName.value, todoListID = todoListID, parentTodoID = null)
                }
                coroutineScope.launch { onSave(todo) }
                todoName.value = ""
            }) {
                Text(text = stringResource(R.string.save_button_label))
            }
        }
    }
}