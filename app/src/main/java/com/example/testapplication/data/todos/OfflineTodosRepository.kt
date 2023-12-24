package com.example.testapplication.data.todos

import androidx.sqlite.db.SimpleSQLiteQuery
import kotlinx.coroutines.flow.Flow


class OfflineTodosRepository(private val itemDao: TodoDao) {
    fun getAllTodosStream(): Flow<List<Todo>> = itemDao.getAllTodos()

    fun getTodoStream(id: Int): Flow<Todo?> = itemDao.getTodo(id)

    fun getTodoSetStream(listID: Int, ascending: Boolean): Flow<List<Todo>> {
        var queryString = "SELECT * from Todo WHERE todoListID = "
        queryString += listID
        queryString += " ORDER BY completed "
        queryString += if (ascending) {
            "ASC"
        } else {
            "DESC"
        }
        val query = SimpleSQLiteQuery(queryString, emptyArray())
        return itemDao.getTodoSet(query = query)
    }

    suspend fun insertTodo(todo: Todo) = itemDao.insert(todo)

    suspend fun deleteTodo(todo: Todo) = itemDao.delete(todo)

    suspend fun updateTodo(todo: Todo) = itemDao.update(todo)
}