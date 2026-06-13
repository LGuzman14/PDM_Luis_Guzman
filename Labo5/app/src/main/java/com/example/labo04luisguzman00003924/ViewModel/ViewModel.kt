package com.example.labo04luisguzman00003924.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.labo04luisguzman00003924.Model.Task
import com.example.labo04luisguzman00003924.data.repository.TaskRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Date

class GeneralViewModel(
    private val repository: TaskRepository
) : ViewModel() {

    val tasks: StateFlow<List<Task>> = repository.getAllTasks()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    fun addTask(title: String, description: String) {
        val cleanTitle = title.trim()
        val cleanDescription = description.trim()

        if (cleanTitle.isBlank()) return

        viewModelScope.launch {
            repository.insertTask(
                Task(
                    title = cleanTitle,
                    description = cleanDescription,
                    endDate = Date()
                )
            )
        }
    }

    fun updateTask(task: Task, title: String, description: String) {
        val cleanTitle = title.trim()
        val cleanDescription = description.trim()

        if (cleanTitle.isBlank()) return

        viewModelScope.launch {
            repository.updateTask(
                task.copy(
                    title = cleanTitle,
                    description = cleanDescription
                )
            )
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            repository.deleteTask(task)
        }
    }

    fun toggleTaskStatus(task: Task) {
        viewModelScope.launch {
            repository.updateTask(
                task.copy(isCompleted = !task.isCompleted)
            )
        }
    }

    class Factory(
        private val repository: TaskRepository
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(GeneralViewModel::class.java)) {
                return GeneralViewModel(repository) as T
            }

            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
