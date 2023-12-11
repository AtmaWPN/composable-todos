package com.example.testapplication.data.todos

import kotlinx.coroutines.flow.Flow

/**
 * Repository that provides insert, update, delete, and retrieve of todos from a given data source.
 */
interface TodosRepository {
    /**
     * Retrieve all the items from the the given data source.
     */
    fun getAllTodosStream(): Flow<List<Todo>>

    /**
     * Retrieve an item from the given data source that matches with the [id].
     */
    fun getTodoStream(id: Int): Flow<Todo?>

    fun getTodoSetStream(ascending: Boolean): Flow<List<Todo>>

    /**
     * Insert item in the data source
     */
    suspend fun insertTodo(todo: Todo)

    /**
     * Delete item from the data source
     */
    suspend fun deleteTodo(todo: Todo)

    /**
     * Update item in the data source
     */
    suspend fun updateTodo(todo: Todo)
}