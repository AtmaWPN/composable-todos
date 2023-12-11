package com.example.testapplication.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RawQuery
import androidx.room.Update
import androidx.sqlite.db.SupportSQLiteQuery
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoListDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(todoList: TodoList)

    @Update
    suspend fun update(todoList: TodoList)

    @Delete
    suspend fun delete(todoList: TodoList)

    @Query("SELECT * from TodoList WHERE id = :id")
    fun getTodoList(id: Int) : Flow<TodoList>

    @Query("SELECT * from TodoList ORDER BY name ASC")
    fun getAllTodoLists() : Flow<List<TodoList>>

    @RawQuery(observedEntities = [TodoList::class])
    fun getTodoLists(query: SupportSQLiteQuery) : Flow<List<TodoList>>
}