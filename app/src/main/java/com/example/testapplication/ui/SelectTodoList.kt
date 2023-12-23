package com.example.testapplication.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.testapplication.data.todolists.TodoList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@Composable
fun ListDropdown(todoListFlow: Flow<List<TodoList>>, modifier: Modifier = Modifier, activeListName: String = "dropdown",
                 onSelectList: suspend (TodoList) -> Unit,
                 onSave: suspend (TodoList) -> Unit) {

    val coroutineScope = rememberCoroutineScope()

    val todoLists: List<TodoList> by todoListFlow.collectAsStateWithLifecycle(initialValue = emptyList(),
        lifecycleOwner = LocalLifecycleOwner.current)

    val expanded = remember { mutableStateOf(false) }
    val popupControl = remember { mutableStateOf(false) }

    Box(modifier = Modifier
        .wrapContentSize()) {
        Button(onClick = { expanded.value = true },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Blue),
            contentPadding = PaddingValues(all = 5.dp)
        ) {
            Text(text = activeListName,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = modifier
                    .padding(16.dp)
                    .fillMaxWidth())
        }
        DropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false },
            modifier = modifier
                .fillMaxWidth()
        ) {
            for (todoList in todoLists) {
                DropdownMenuItem(text = { Text(todoList.name,
                    fontSize = 30.sp,
                    textAlign = TextAlign.Center,
                    modifier = modifier
                        .padding(4.dp)
                        .fillMaxWidth()) },
                    onClick = { coroutineScope.launch { onSelectList(todoList) }
                        expanded.value = false })
                Divider()
            }
            DropdownMenuItem(text = { Text(text = "new Todo List",
                fontSize = 30.sp,
                textAlign = TextAlign.Center,
                modifier = modifier
                    .padding(4.dp)
                    .fillMaxWidth()) },
                onClick = {
                    popupControl.value = true
                    expanded.value = false })
        }
    }
    if (popupControl.value) {
        Popup(alignment = Alignment.Center,
            onDismissRequest = { popupControl.value = false },
            properties = PopupProperties(focusable = true)
        ) {
            Surface(color = Color.Yellow) {
                CreateTodoList(onSave = onSave, modifier = Modifier.padding(24.dp))
            }
        }
    }
}