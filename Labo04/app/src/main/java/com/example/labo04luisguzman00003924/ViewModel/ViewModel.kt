package com.example.labo04luisguzman00003924.ViewModel

import androidx.lifecycle.ViewModel
import com.example.labo04luisguzman00003924.Model.Task
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Date

class GeneralViewModel: ViewModel() {
    private val _tasks = MutableStateFlow<MutableList<Task>>(mutableListOf())
    val tasks = _tasks.asStateFlow()

    init {
        generateRandomTasks()
    }

    private fun generateRandomTasks() {
        val randomTasks = mutableListOf<Task>()

        val titles = listOf(
            "Estudiar para el parcial",
            "Entregar laboratorio de Android",
            "Revisar apuntes de clase",
            "Crear pantalla en Jetpack Compose",
            "Corregir errores del proyecto",
            "Subir tarea al aula virtual",
            "Preparar exposición grupal",
            "Leer guía de laboratorio",
            "Practicar ejercicios de Kotlin",
            "Diseñar interfaz de usuario",
            "Actualizar repositorio en GitHub",
            "Hacer pruebas de la aplicación",
            "Organizar archivos del proyecto",
            "Completar documentación técnica",
            "Enviar avance al docente"
        )

        val descriptions = listOf(
            "Actividad relacionada con la universidad",
            "Pendiente importante para completar esta semana",
            "Revisar cuidadosamente antes de entregar",
            "Tarea necesaria para avanzar en el proyecto",
            "Completar y verificar que funcione correctamente",
            "Realizar con tiempo para evitar errores",
            "Trabajo asignado en la clase de programación",
            "Requiere concentración y revisión final",
            "Pendiente académico que debe ser entregado pronto",
            "Actividad práctica para mejorar el proyecto"
        )

        for (i in 1..15) {
            randomTasks.add(
                Task(
                    id = i,
                    title = titles.random(),
                    description = descriptions.random(),
                    endDate = Date()
                )
            )
        }

        _tasks.value = randomTasks
    }

    fun addTask(task: Task) {
        _tasks.value = _tasks.value.toMutableList().apply { add(task) }
    }
}