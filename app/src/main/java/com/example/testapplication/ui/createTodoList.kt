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
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateTodoList(onSave: suspend (TodoList) -> Unit, modifier: Modifier = Modifier) {
    val coroutineScope = rememberCoroutineScope()
    val listName = remember { mutableStateOf("") }
    Column(modifier = modifier) {
        TextField(value = listName.value, onValueChange = { listName.value = it })
        Button(onClick = {
            if (listName.value == "") {
                return@Button
            }
            val newList = TodoList(name = listName.value)
            coroutineScope.launch { onSave(newList) }
            listName.value = ""
        }) {
            Text(text = "Save")
        }
    }
}