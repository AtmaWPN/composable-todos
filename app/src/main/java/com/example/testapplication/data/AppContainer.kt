package com.example.testapplication.data

import TodosRepository
import android.content.Context

/**
 * App container for Dependency injection.
 */
interface AppContainer {
    val todosRepository: TodosRepository
}

/**
 * [AppContainer] implementation that provides instance of [OfflineTodosRepository]
 */
class AppDataContainer(private val context: Context) : AppContainer {
    /**
     * Implementation for [TodosRepository]
     */
    override val todosRepository: TodosRepository by lazy {
        OfflineTodosRepository(TodoListDatabase.getDatabase(context).todoDao())
    }
}