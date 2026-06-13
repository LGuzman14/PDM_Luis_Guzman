package com.example.labo04luisguzman00003924.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.labo04luisguzman00003924.Model.Task
import com.example.labo04luisguzman00003924.ViewModel.GeneralViewModel
import com.example.labo04luisguzman00003924.component.TaskCard

@Composable
fun TODOScreen(
    viewModel: GeneralViewModel,
    onNavigateBack: () -> Unit
) {
    val tasks by viewModel.tasks.collectAsState()

    var showTaskDialog by remember { mutableStateOf(false) }
    var taskTitle by remember { mutableStateOf("") }
    var taskDescription by remember { mutableStateOf("") }
    var taskToEdit by remember { mutableStateOf<Task?>(null) }
    var taskToDelete by remember { mutableStateOf<Task?>(null) }

    fun clearForm() {
        taskTitle = ""
        taskDescription = ""
        taskToEdit = null
        showTaskDialog = false
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 24.dp)
        ) {
            Text(
                text = "Mis tareas",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Agrega, modifica y elimina tareas guardadas con Room",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(50),
                    color = MaterialTheme.colorScheme.surface
                ) {
                    Text(
                        text = "${tasks.size} tareas",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedButton(
                        onClick = { onNavigateBack() },
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(text = "Volver")
                    }

                    Button(
                        onClick = {
                            taskToEdit = null
                            taskTitle = ""
                            taskDescription = ""
                            showTaskDialog = true
                        },
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(text = "Añadir")
                    }
                }
            }
        }

        HorizontalDivider(
            color = MaterialTheme.colorScheme.outlineVariant
        )

        if (tasks.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(28.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "No hay tareas guardadas",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Presiona Añadir para guardar tu primera tarea en la base de datos local.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(
                    start = 20.dp,
                    end = 20.dp,
                    top = 16.dp,
                    bottom = 24.dp
                )
            ) {
                items(
                    items = tasks,
                    key = { task -> task.id }
                ) { task ->
                    TaskCard(
                        task = task,
                        onEdit = { selectedTask ->
                            taskToEdit = selectedTask
                            taskTitle = selectedTask.title
                            taskDescription = selectedTask.description
                            showTaskDialog = true
                        },
                        onDelete = { selectedTask ->
                            taskToDelete = selectedTask
                        },
                        onToggleStatus = { selectedTask ->
                            viewModel.toggleTaskStatus(selectedTask)
                        }
                    )
                }
            }
        }
    }

    if (showTaskDialog) {
        val editingTask = taskToEdit

        AlertDialog(
            onDismissRequest = {
                clearForm()
            },
            title = {
                Text(
                    text = if (editingTask == null) "Nueva tarea" else "Modificar tarea",
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Column {
                    OutlinedTextField(
                        value = taskTitle,
                        onValueChange = { taskTitle = it },
                        label = { Text("Nombre de la tarea") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = taskDescription,
                        onValueChange = { taskDescription = it },
                        label = { Text("Descripción") },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 3
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (taskTitle.isNotBlank()) {
                            if (editingTask == null) {
                                viewModel.addTask(
                                    title = taskTitle,
                                    description = taskDescription
                                )
                            } else {
                                viewModel.updateTask(
                                    task = editingTask,
                                    title = taskTitle,
                                    description = taskDescription
                                )
                            }

                            clearForm()
                        }
                    },
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(text = if (editingTask == null) "Guardar" else "Actualizar")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        clearForm()
                    }
                ) {
                    Text("Cancelar")
                }
            }
        )
    }

    taskToDelete?.let { selectedTask ->
        AlertDialog(
            onDismissRequest = {
                taskToDelete = null
            },
            title = {
                Text(
                    text = "Eliminar tarea",
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Text(
                    text = "¿Deseas eliminar \"${selectedTask.title}\"? Esta acción quitará la tarea de Room."
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.deleteTask(selectedTask)
                        taskToDelete = null
                    },
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Eliminar")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        taskToDelete = null
                    }
                ) {
                    Text("Cancelar")
                }
            }
        )
    }
}
