package com.example.hackaton2025.data.fakeData

import com.example.hackaton2025.tasks.TaskItem
import java.time.LocalDateTime

data object FakeData {
    private val now = LocalDateTime.now()

    val allTasks = listOf(
        TaskItem(
            title = "Dry clothes from the washer",
            description = "You need to clean the filters first.",
            deadline = now.withHour(20),
            price = 3,
            isDone = false
        ),
        TaskItem(
            title = "Take out the trash",
            deadline = now.withHour(20),
            price = 2,
            isDone = true
        ),
        TaskItem(
            title = "Empty the dishwasher",
            description = "Also make sure you empty the sink, please.",
            deadline = now.plusDays(1),
            price = 5,
            isDone = false
        ),
        TaskItem(
            title = "Clean your room",
            deadline = now.plusDays(4),
            price = 4,
            isDone = false
        ),
        TaskItem(
            title = "Vacuum the entire house",
            deadline = now.plusDays(2).withHour(18),
            price = 10,
            isDone = true
        ),
        TaskItem(
            title = "Water the plants",
            description = "Please don't forget about the ones from the balcony like you did last time.",
            deadline = now.plusDays(6),
            price = 5,
            isDone = false
        ),
        TaskItem(
            title = "Help dad to install the new desktop",
            description = "This has been delayed for too long",
            deadline = now.plusDays(3),
            price = 5,
            isDone = false
        ),
    )
}