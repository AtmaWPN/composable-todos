package com.example.testapplication.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Todo::class], version = 1, exportSchema = false)
abstract class TodoListDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDao

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