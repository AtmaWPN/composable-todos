package com.example.testapplication.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Delete
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.testapplication.data.Todo
import kotlinx.coroutines.launch

@Composable
fun MainTodos(mainViewModel: TodoViewModel, modifier: Modifier = Modifier) {
    // A surface container using the 'background' color from the theme
    Surface(modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background) {

        val popupControl = remember { mutableStateOf(false) }

        Column {
            TodoList(label = "Todos For This App", mainViewModel)

            FloatingActionButton(onClick = { popupControl.value = true }) {
                Text(text = "Create Todo", fontSize = 24.sp, modifier = Modifier.padding(12.dp))
            }
        }
        if (popupControl.value) {
            Popup(alignment = Alignment.Center,
                onDismissRequest = { popupControl.value = false },
                properties = PopupProperties(focusable = true)) {
                Surface(color = Color.Yellow) {
                    CreateTodo(viewModel = mainViewModel, modifier = Modifier.padding(24.dp))
                }
            }
        }
    }
}

@Composable
fun TodoList(label: String, viewModel: TodoViewModel, modifier: Modifier = Modifier) {
    val coroutineScope = rememberCoroutineScope()

    val todos: List<Todo> by viewModel.todos.collectAsStateWithLifecycle(initialValue = emptyList(),
        lifecycleOwner = LocalLifecycleOwner.current)

    Column(modifier = modifier.verticalScroll(rememberScrollState())) {
        Text(
            text = label,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = modifier.padding(16.dp).fillMaxWidth()
        )
        for (todo in todos) {
            TodoItem(todo, onComplete = { coroutineScope.launch {
                todo.completed = it
                viewModel.dataContainer.todosRepository.updateTodo(todo)
            }}, onDelete = {
            coroutineScope.launch {
                viewModel.dataContainer.todosRepository.deleteTodo(todo)
            }})
        }
//        TodoItem(name = "Populate from local database")
//        TodoItem(name = "Center to do text vertically", done = true)
//        TodoItem(name = "Sketch UI layout")
    }
}

@Composable
fun TodoItem(todo: Todo, onComplete: ((Boolean) -> Unit),
             onDelete: () -> Unit,
             modifier: Modifier = Modifier) {
    val checkedState = remember { mutableStateOf(todo.completed) }
    Row (verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth()) {
        Checkbox(checked = todo.completed,
            onCheckedChange = { checkedState.value = it
                onComplete(it)},
            colors = CheckboxDefaults.colors(
                uncheckedColor = Color.Red,
                checkedColor = Color.Green,
                checkmarkColor = Color.Yellow
            ))
        Text(
            text = todo.taskText,
            fontSize = 24.sp,
            modifier = Modifier.weight(1f),
        )
        IconButton(onClick = onDelete) {
            Icon(Icons.Sharp.Delete, "delete to-do button", tint = Color.Red)
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewTodo() {

}