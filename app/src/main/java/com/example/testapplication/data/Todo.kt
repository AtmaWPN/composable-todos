package com.example.testapplication.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "TodoList")
data class Todo(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val taskText: String,
    var completed: Boolean = false
)