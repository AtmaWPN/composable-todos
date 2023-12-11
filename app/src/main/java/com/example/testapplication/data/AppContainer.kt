package com.example.testapplication.data

import android.content.Context
import com.example.testapplication.data.todos.OfflineTodosRepository
import com.example.testapplication.data.todos.TodosRepository

/**
 * App container for Dependency injection.
 */
interface AppContainer {
    val todosRepository: OfflineTodosRepository
    val todoListRepository: OfflineTodoListRepository
}

/**
 * [AppContainer] implementation that provides instance of [OfflineTodosRepository]
 */
class AppDataContainer(private val context: Context) : AppContainer {
    /**
     * Implementation for [TodosRepository]
     */
    override val todosRepository: OfflineTodosRepository by lazy {
        OfflineTodosRepository(TodoListDatabase.getDatabase(context).todoDao())
    }

    /**
     * Implementation for [TodoListRepository]
     */
    override val todoListRepository: OfflineTodoListRepository by lazy {
        OfflineTodoListRepository(TodoListDatabase.getDatabase(context).todoListDao())
    }
}