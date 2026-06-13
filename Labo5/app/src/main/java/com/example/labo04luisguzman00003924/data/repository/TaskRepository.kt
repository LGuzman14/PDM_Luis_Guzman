package com.example.labo04luisguzman00003924.data.repository

import com.example.labo04luisguzman00003924.Model.Task
import com.example.labo04luisguzman00003924.data.local.TaskDao
import com.example.labo04luisguzman00003924.data.local.toEntity
import com.example.labo04luisguzman00003924.data.local.toTask
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TaskRepository(private val taskDao: TaskDao) {
    fun getAllTasks(): Flow<List<Task>> {
        return taskDao.getAllTasks().map { taskEntities ->
            taskEntities.map { taskEntity -> taskEntity.toTask() }
        }
    }

    suspend fun insertTask(task: Task) {
        taskDao.insertTask(task.toEntity())
    }

    suspend fun updateTask(task: Task) {
        taskDao.updateTask(task.toEntity())
    }

    suspend fun deleteTask(task: Task) {
        taskDao.deleteTask(task.toEntity())
    }
}
