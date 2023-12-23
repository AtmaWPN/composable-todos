package com.example.testapplication.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.testapplication.data.todolists.TodoList
import com.example.testapplication.data.todolists.TodoListDao
import com.example.testapplication.data.todos.Todo
import com.example.testapplication.data.todos.TodoDao

@Database(entities = [Todo::class, TodoList::class], version = 4, exportSchema = false)
abstract class TodoListDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDao
    abstract fun todoListDao(): TodoListDao

    companion object {
        @Volatile
        private var Instance: TodoListDatabase? = null

        fun getDatabase(context: Context) : TodoListDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, TodoListDatabase::class.java, "todolist_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}