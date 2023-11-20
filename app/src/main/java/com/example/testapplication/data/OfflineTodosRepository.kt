package com.example.testapplication.data

import TodosRepository
import kotlinx.coroutines.flow.Flow

class OfflineTodosRepository(private val itemDao: TodoDao) : TodosRepository {
    override fun getAllTodosStream(): Flow<List<Todo>> = itemDao.getAllTodos()

    override fun getTodoStream(id: Int): Flow<Todo?> = itemDao.getTodo(id)

    override suspend fun insertTodo(todo: Todo) = itemDao.insert(todo)

    override suspend fun deleteTodo(todo: Todo) = itemDao.delete(todo)

    override suspend fun updateTodo(todo: Todo) = itemDao.update(todo)
}