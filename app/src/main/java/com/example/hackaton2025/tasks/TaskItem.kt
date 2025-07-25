package com.example.hackaton2025.tasks

import java.time.LocalDateTime

data class TaskItem(
    val title: String,
    val description: String = "",
    val deadline: LocalDateTime,
    val price: Int,
    val isDone: Boolean
)
