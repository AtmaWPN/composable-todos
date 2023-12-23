package com.example.testapplication.data.todolists

import androidx.sqlite.db.SimpleSQLiteQuery
import kotlinx.coroutines.flow.Flow


class OfflineTodoListRepository(private val itemDao: TodoListDao) {
    fun getAllTodoListsStream(): Flow<List<TodoList>> = itemDao.getAllTodoLists()

    fun getTodoListStream(id: Int): Flow<TodoList> = itemDao.getTodoList(id)

    fun getTodoListsStream(ascending: Boolean): Flow<List<TodoList>> {
        var queryString = "SELECT * from TodoList ORDER BY completed "
        queryString += if (ascending) {
            "ASC"
        } else {
            "DESC"
        }
        val query = SimpleSQLiteQuery(queryString, emptyArray())
        return itemDao.getTodoLists(query = query)
    }

    suspend fun insertTodoList(todoList: TodoList) = itemDao.insert(todoList)

    suspend fun deleteTodoList(todoList: TodoList) = itemDao.delete(todoList)

    suspend fun updateTodoList(todoList: TodoList) = itemDao.update(todoList)
}