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
import androidx.compose.ui.unit.dp
import com.example.testapplication.R
import com.example.testapplication.data.todolists.TodoList
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateTodoList(onSave: suspend (TodoList) -> Unit, modifier: Modifier = Modifier) {
    val coroutineScope = rememberCoroutineScope()
    val listName = remember { mutableStateOf("") }
    Surface(border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSurface)) {
        Column(modifier = modifier) {
            TextField(label = { Text(text = stringResource(R.string.list_input_label)) }, value = listName.value, onValueChange = { listName.value = it })
            Button(onClick = {
                if (listName.value == "") {
                    return@Button
                }
                val newList = TodoList(name = listName.value)
                coroutineScope.launch { onSave(newList) }
                listName.value = ""
            }) {
                Text(text = stringResource(R.string.save_button_label))
            }
        }
    }
}