package com.example.hackaton2025.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hackaton2025.tasks.TaskCard
import com.example.hackaton2025.tasks.TaskItem


@Composable
fun MainTasks() {

    val now = java.time.LocalDateTime.now()
    val tomorrowSameTime = now.plusDays(1)
    val twoDaysLater = now.plusDays(2)

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
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        items.forEach { item ->
            TaskCard(item)
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}



