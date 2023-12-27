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
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.testapplication.R
import com.example.testapplication.data.getHomeTodoList
import com.example.testapplication.data.setHomeList
import com.example.testapplication.data.todolists.TodoList
import com.example.testapplication.data.todos.Todo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@Composable
fun MainTodos(mainViewModel: TodoViewModel, modifier: Modifier = Modifier) {
    val context = LocalContext.current

    val homeListId: Int by getHomeTodoList(context).collectAsStateWithLifecycle(
        initialValue = -1,
        lifecycleOwner = LocalLifecycleOwner.current
    )
    // TODO: should this be a LaunchedEffect?
    val homeTodoListFlow = mainViewModel.dataContainer.todoListRepository.getTodoListStream(homeListId)

    val homeTodoList: TodoList by homeTodoListFlow.collectAsStateWithLifecycle(
        initialValue = TodoList(id=-1, name = stringResource(R.string.default_list_name)),
        lifecycleOwner = LocalLifecycleOwner.current
    )

    val nonNullHomeList = homeTodoList ?: TodoList(name = stringResource(R.string.default_list_name))

    Surface(modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background) {

        val popupControl = remember { mutableStateOf(false) }

        Column {
            ListDropdown(todoListFlow = mainViewModel.dataContainer.todoListRepository.getAllTodoListsStream(),
                activeListName = nonNullHomeList.name,
                onSelectList = { setHomeList(newListId = it.id, context) },
                onSave = { mainViewModel.dataContainer.todoListRepository.insertTodoList(it) })
            TodoList(todosFlow = mainViewModel.getTodos(homeListId),
                updateTodo = { mainViewModel.dataContainer.todosRepository.updateTodo(it) },
                deleteTodo = { mainViewModel.dataContainer.todosRepository.deleteTodo(it) })

            FloatingActionButton(onClick = { popupControl.value = true }) {
                Text(text = stringResource(R.string.new_todo_button_label), fontSize = 24.sp, modifier = Modifier.padding(12.dp))
            }
        }
        if (popupControl.value) {
            Popup(alignment = Alignment.Center,
                onDismissRequest = { popupControl.value = false },
                properties = PopupProperties(focusable = true)) {
                CreateTodo(onSave = { mainViewModel.dataContainer.todosRepository.insertTodo(it) },
                    setDefaultList = {
                        mainViewModel.dataContainer.todoListRepository.insertTodoList(it)
                        setHomeList(newListId = 1, context)
                    }, todoListID = homeListId, modifier = Modifier.padding(24.dp))

            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TodoList(todosFlow: Flow<List<Todo>>, updateTodo: suspend (Todo) -> Unit,
             deleteTodo: suspend (Todo) -> Unit, modifier: Modifier = Modifier) {

    val recomposeToggle = remember { mutableStateOf(false) }

    LaunchedEffect(recomposeToggle.value) {}

    val todos: List<Todo> by todosFlow.collectAsStateWithLifecycle(initialValue = emptyList(),
        lifecycleOwner = LocalLifecycleOwner.current)

    LazyColumn {
        items(todos) { todo ->
            TodoItem(todo = todo, updateTodo = updateTodo,
                deleteTodo = deleteTodo,
                recomposeToggle,
                modifier.animateItemPlacement())
        }
    }
}

@Composable
fun TodoItem(todo: Todo, updateTodo: suspend (Todo) -> Unit,
             deleteTodo: suspend (Todo) -> Unit,
             recomposeToggle: MutableState<Boolean>,
             modifier: Modifier = Modifier) {

    val coroutineScope = rememberCoroutineScope()

    val checkedState = remember { mutableStateOf(todo.completed) }

    Row (verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth()) {
        Checkbox(checked = todo.completed,
            onCheckedChange = { checkedState.value = it
                todo.completed = it
                recomposeToggle.value = !recomposeToggle.value
                coroutineScope.launch { updateTodo(todo) } })
        Text(
            text = todo.taskText,
            fontSize = 24.sp,
            modifier = Modifier.weight(1f),
        )
        IconButton(onClick = { coroutineScope.launch { deleteTodo(todo) } }) {
            Icon(Icons.Sharp.Delete, stringResource(R.string.delete_button_description), tint = MaterialTheme.colorScheme.error)
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewTodo() {

}