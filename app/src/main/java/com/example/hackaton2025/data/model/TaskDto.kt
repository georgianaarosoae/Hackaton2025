package com.example.hackaton2025.data.model

import java.time.LocalDateTime

data class TaskDto(
    val title: String,
    val description: String,
    val deadline: String,
    val value: Float,
    val status: TaskStatus
)

enum class TaskStatus {
    ACTIVE, COMPLETED
}