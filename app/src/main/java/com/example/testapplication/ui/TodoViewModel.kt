package com.example.testapplication.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.testapplication.data.AppDataContainer

class TodoViewModel(application: Application) : AndroidViewModel(application) {
    // in here we keep the AppDataContainer
    // and expose observable state to the UI layer
    var dataContainer = AppDataContainer(application)

    var todos = dataContainer.todosRepository.getAllTodosStream()
}