package com.example.testapplication.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.testapplication.data.TodoList
import com.example.testapplication.data.dataStore
import com.example.testapplication.data.getHomeTodoList
import com.example.testapplication.data.setHomeList
import com.example.testapplication.data.todos.Todo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@Composable
fun MainTodos(mainViewModel: TodoViewModel, modifier: Modifier = Modifier) {
    // A surface container using the 'background' color from the theme
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val preferences = context.dataStore

    val homeListId: Int by getHomeTodoList(context).collectAsStateWithLifecycle(
        initialValue = -1,
        lifecycleOwner = LocalLifecycleOwner.current
    )
    val homeTodoListFlow = mainViewModel.dataContainer.todoListRepository.getTodoListStream(homeListId)

    println(homeListId)

    val homeTodoList: TodoList by homeTodoListFlow.collectAsStateWithLifecycle(
        initialValue = TodoList(id=-1, name = "default list"),
        lifecycleOwner = LocalLifecycleOwner.current
    )

    val nonNullHomeList = homeTodoList ?: TodoList(name = "default list")

    Surface(modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background) {

        val popupControl = remember { mutableStateOf(false) }

        Column {
            Text(
                text = nonNullHomeList.name,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            )
            TodoList(todosFlow = mainViewModel.getTodos(homeListId),
                updateTodo = { coroutineScope.launch {
                mainViewModel.dataContainer.todosRepository.updateTodo(it)
            } }, deleteTodo = { coroutineScope.launch {
                mainViewModel.dataContainer.todosRepository.deleteTodo(it)
            } })

            FloatingActionButton(onClick = { popupControl.value = true }) {
                Text(text = "Create Todo", fontSize = 24.sp, modifier = Modifier.padding(12.dp))
            }
            FloatingActionButton(onClick = { /*TODO change the sort order to see if it updates the todo set*/ }) {
                
            }
        }
        if (popupControl.value) {
            Popup(alignment = Alignment.Center,
                onDismissRequest = { popupControl.value = false },
                properties = PopupProperties(focusable = true)) {
                Surface(color = Color.Yellow) {
                    CreateTodo(onSave = { coroutineScope.launch {
                        mainViewModel.dataContainer.todosRepository.insertTodo(it)
                    } }, setDefaultList = { coroutineScope.launch {
                        mainViewModel.dataContainer.todoListRepository.insertTodoList(it)
                        setHomeList(newListId = 1, context)
                    } }, todoListID = homeListId, modifier = Modifier.padding(24.dp))
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TodoList(todosFlow: Flow<List<Todo>>, updateTodo: (Todo) -> Unit,
             deleteTodo: (Todo) -> Unit, modifier: Modifier = Modifier) {

    val todos: List<Todo> by todosFlow.collectAsStateWithLifecycle(initialValue = emptyList(),
        lifecycleOwner = LocalLifecycleOwner.current)

    LazyColumn {
        items(todos) { todo ->
            TodoItem(todo = todo, onComplete = {
                todo.completed = it
                updateTodo(todo)
            }, onDelete = { deleteTodo(todo) },
                modifier.animateItemPlacement())
        }
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