package com.example.hackaton2025.data.fakeData

import com.example.hackaton2025.R
import com.example.hackaton2025.tasks.TaskItem
import java.time.LocalDateTime

data object FakeData {
    private val now = LocalDateTime.now()

    val allTasks = listOf(
        TaskItem(
            title = "Help your brother with his math homework.",
            description = "Make sure he understands the homework",
            deadline = now.withHour(21),
            price = 5,
            isDone = true
        ),
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
        TaskItem(
            title = "Clean the litter box",
            deadline = now.plusDays(6),
            price = 5,
            isDone = false
        ),
    )

    internal val wishilistSuggestionItems = listOf(
        "Summer camp",
        "Bike",
        "Football shoes"
    )

    val wishlistData = listOf(       // TODO eliminina
        WishlistData("Lego", 40f, R.drawable.lego, 2),
        WishlistData("Bicycle", 600f, R.drawable.bike, 3),
        WishlistData("New Headphones", 70f, R.drawable.headphones, 5),
        WishlistData("Board Game", 20f, R.drawable.board_game, 7),
        WishlistData("Game Controller", 75f, R.drawable.controller, 8),
    )
}