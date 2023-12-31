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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.testapplication.R
import com.example.testapplication.data.getHomeTodoList
import com.example.testapplication.data.setHomeList
import com.example.testapplication.data.todolists.TodoList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@Composable
fun ListManager(viewModel: TodoViewModel, modifier: Modifier = Modifier) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    val todoListsFlow = viewModel.dataContainer.todoListRepository.getAllTodoListsStream()
    Surface(modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background) {
        Column {
            Text(text = stringResource(R.string.list_manager_label),
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = modifier
                    .padding(16.dp)
                    .fillMaxWidth())
            TodoListColumn(todoListsFlow = todoListsFlow, deleteTodoList = { coroutineScope.launch {
                viewModel.dataContainer.todoListRepository.deleteTodoList(it)
                val currentHomeListId = getHomeTodoList(context).first()
                if (it.id == currentHomeListId) {
                    val currentTodoLists = viewModel.dataContainer.todoListRepository.getAllTodoListsStream().first()
                    val firstListId = currentTodoLists[0].id
                    setHomeList(firstListId, context)
                }
            } })
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TodoListColumn(todoListsFlow: Flow<List<TodoList>>, deleteTodoList: (TodoList) -> Unit,
                   modifier: Modifier = Modifier) {

    val todoLists: List<TodoList> by todoListsFlow.collectAsStateWithLifecycle(initialValue = emptyList(),
        lifecycleOwner = LocalLifecycleOwner.current)

    LazyColumn {
        items(todoLists) { todoList ->
            TodoListItem(todoList = todoList, onDelete = { deleteTodoList(todoList) },
                modifier.animateItemPlacement())
        }
    }
}

@Composable
fun TodoListItem(todoList: TodoList, onDelete: () -> Unit, modifier: Modifier = Modifier) {
    Row (verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth()) {
        Text(
            text = todoList.name,
            fontSize = 24.sp,
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp)
        )
        IconButton(onClick = onDelete) {
            Icon(Icons.Sharp.Delete, stringResource(R.string.delete_button_description), tint = Color.Red)
        }
    }
}
