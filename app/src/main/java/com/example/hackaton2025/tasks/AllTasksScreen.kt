package com.example.hackaton2025.tasks

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun AllTasksScreen() {

    val now = java.time.LocalDateTime.now()
    val tomorrowSameTime = now.plusDays(1)
    val twoDaysLater = now.plusDays(2)

    val scrollState = rememberScrollState()

    val items = listOf(
        TaskItem(
            title = "Clean room",
            description = "",
            date = tomorrowSameTime.toString(),
            price = "$10.00",
            backgroundColor = Color(0xFFE4F3FD),
            isDone = false
        ),
        TaskItem(
            title = "Do Homework",
            description = "",
            date = twoDaysLater.toString(),
            price = "$15.00",
            backgroundColor = Color(0xFFE3F7E7),
            isDone = true
        ),
        TaskItem(
            title = "Clean room",
            description = "",
            date = tomorrowSameTime.toString(),
            price = "$10.00",
            backgroundColor = Color(0xFFE4F3FD),
            isDone = false
        ),
        TaskItem(
            title = "Do Homework",
            description = "",
            date = twoDaysLater.toString(),
            price = "$15.00",
            backgroundColor = Color(0xFFE3F7E7),
            isDone = true
        ),
        TaskItem(
            title = "Clean room",
            description = "",
            date = tomorrowSameTime.toString(),
            price = "$10.00",
            backgroundColor = Color(0xFFE4F3FD),
            isDone = false
        ),
        TaskItem(
            title = "Do Homework",
            description = "",
            date = twoDaysLater.toString(),
            price = "$15.00",
            backgroundColor = Color(0xFFE3F7E7),
            isDone = true
        ),
        TaskItem(
            title = "Clean room",
            description = "",
            date = tomorrowSameTime.toString(),
            price = "$10.00",
            backgroundColor = Color(0xFFE4F3FD),
            isDone = false
        ),
        TaskItem(
            title = "Do Homework",
            description = "",
            date = twoDaysLater.toString(),
            price = "$15.00",
            backgroundColor = Color(0xFFE3F7E7),
            isDone = true
        ),
        TaskItem(
            title = "Clean room",
            description = "",
            date = tomorrowSameTime.toString(),
            price = "$10.00",
            backgroundColor = Color(0xFFE4F3FD),
            isDone = false
        ),
        TaskItem(
            title = "Do Homework",
            description = "",
            date = twoDaysLater.toString(),
            price = "$15.00",
            backgroundColor = Color(0xFFE3F7E7),
            isDone = true
        ),
        TaskItem(
            title = "Clean room",
            description = "",
            date = tomorrowSameTime.toString(),
            price = "$10.00",
            backgroundColor = Color(0xFFE4F3FD),
            isDone = false
        ),
        TaskItem(
            title = "Do Homework",
            description = "",
            date = twoDaysLater.toString(),
            price = "$15.00",
            backgroundColor = Color(0xFFE3F7E7),
            isDone = true
        ),
        TaskItem(
            title = "Clean room",
            description = "",
            date = tomorrowSameTime.toString(),
            price = "$10.00",
            backgroundColor = Color(0xFFE4F3FD),
            isDone = false
        ),
        TaskItem(
            title = "Do Homework",
            description = "",
            date = twoDaysLater.toString(),
            price = "$15.00",
            backgroundColor = Color(0xFFE3F7E7),
            isDone = true
        )
    )

    Column(
        modifier = Modifier
            .verticalScroll(scrollState)
            .fillMaxSize()
            .background(Color.White)
    ) {
        items.forEach { item ->
            TaskCard(item)
            Spacer(modifier = Modifier.height(12.dp))
        }
    }

}