package com.example.labo04luisguzman00003924.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.labo04luisguzman00003924.Model.Task
import java.util.Date

@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String,
    val endDate: Long = System.currentTimeMillis(),
    val isCompleted: Boolean = false
)

fun TaskEntity.toTask(): Task {
    return Task(
        id = id,
        title = title,
        description = description,
        endDate = Date(endDate),
        isCompleted = isCompleted
    )
}

fun Task.toEntity(): TaskEntity {
    return TaskEntity(
        id = id,
        title = title,
        description = description,
        endDate = endDate.time,
        isCompleted = isCompleted
    )
}
