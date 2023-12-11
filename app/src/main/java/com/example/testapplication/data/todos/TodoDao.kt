package com.example.testapplication.data.todos

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
interface TodoDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(todo: Todo)

    @Update
    suspend fun update(todo: Todo)

    @Delete
    suspend fun delete(todo: Todo)

    @Query("SELECT * from Todo WHERE id = :id")
    fun getTodo(id: Int) : Flow<Todo>

    @Query("SELECT * from Todo ORDER BY completed ASC")
    fun getAllTodos() : Flow<List<Todo>>

    @RawQuery(observedEntities = [Todo::class])
    fun getTodoSet(query: SupportSQLiteQuery) : Flow<List<Todo>>
}