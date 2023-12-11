package com.example.testapplication.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.testapplication.data.AppDataContainer
import com.example.testapplication.data.todos.Todo
import kotlinx.coroutines.flow.Flow

class TodoViewModel(application: Application) : AndroidViewModel(application) {
    // in here we keep the AppDataContainer
    // and expose observable state to the UI layer
    var dataContainer = AppDataContainer(application)

    fun getTodos(listID: Int): Flow<List<Todo>> {
        return dataContainer.todosRepository.getTodoSetStream(listID, true)
    }
    var todolists = dataContainer.todoListRepository.getAllTodoListsStream()
}