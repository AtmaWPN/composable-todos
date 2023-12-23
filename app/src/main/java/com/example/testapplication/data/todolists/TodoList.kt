package com.example.testapplication.data.todolists

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TodoList (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String
)