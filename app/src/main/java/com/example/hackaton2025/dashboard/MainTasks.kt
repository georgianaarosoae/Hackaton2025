package com.example.hackaton2025.dashboard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.hackaton2025.Constants
import com.example.hackaton2025.data.fakeData.FakeData
import com.example.hackaton2025.tasks.TaskCard
import com.example.hackaton2025.tasks.TaskItem
import java.time.format.DateTimeFormatter


@Composable
fun MainTasks() {
    val items = FakeData.allTasks.sortedBy { it.deadline }.take(3)

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        items.forEach { item ->
            TaskCard(item)
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}



