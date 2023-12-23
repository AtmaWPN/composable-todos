package com.example.testapplication.data.todos

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.testapplication.data.todolists.TodoList

@Entity(foreignKeys = [ForeignKey(entity = TodoList::class,
        parentColumns = ["id"],
    childColumns = ["todoListID"],
    onDelete = ForeignKey.CASCADE)])
data class Todo(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val taskText: String,
    val todoListID: Int,
    val parentTodoID: Int?,
    var completed: Boolean = false
)