package com.example.hackaton2025.tasks

import androidx.compose.ui.graphics.Color

data class TaskItem(
    val title: String,
    val description: String,
    val date: String,
    val price: String,
    val backgroundColor: Color,
    val isDone: Boolean
)
