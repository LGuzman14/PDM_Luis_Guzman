package com.example.labo04luisguzman00003924

import android.app.Application
import com.example.labo04luisguzman00003924.data.local.AppDatabase
import com.example.labo04luisguzman00003924.data.repository.TaskRepository

class TaskApplication : Application() {
    val database: AppDatabase by lazy {
        AppDatabase.getDatabase(this)
    }

    val repository: TaskRepository by lazy {
        TaskRepository(database.taskDao())
    }
}
