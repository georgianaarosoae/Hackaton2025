package com.example.hackaton2025.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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


@Composable
fun MainTasks() {

    val items = listOf(
        ActivityItem(
            title = "Hot Yoga",
            description = "Designed to improve flexibility, build strength, detoxify the body through sw...",
            date = "Jul 24, 2025 at 12:00",
            price = "$10.00",
            backgroundColor = Color(0xFFE4F3FD),
            isDone = false
        ),
        ActivityItem(
            title = "Healthy Breakfast",
            description = "Quinoa Bowl with Avocado and Poached Egg",
            date = "Jul 24, 2025 at 09:10",
            price = "$23.00",
            backgroundColor = Color(0xFFE3F7E7),
            isDone = true
        ),
        ActivityItem(
            title = "Meeting with Alice",
            description = "A brief catch-up to discuss current tasks, share updates, align on priorities, ...",
            date = "Jul 24, 2025 at 10:40",
            price = "$50.00",
            backgroundColor = Color(0xFFE3F7E7),
            isDone = true
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        items.forEach { item ->
            ActivityCard(item)

            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}


@Composable
fun ActivityCard(item: ActivityItem) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(item.backgroundColor, shape = RoundedCornerShape(12.dp))
            .padding(12.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = item.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = item.description,
                    color = Color.Black.copy(alpha = 0.7f),
                    fontSize = 13.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = item.date,
                    color = Color.Gray,
                    fontSize = 12.sp
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Column(
                horizontalAlignment = Alignment.End
            ) {
                if (item.isDone) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Done",
                        tint = Color(0xFF3AC27D),
                        modifier = Modifier.size(20.dp)
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .size(20.dp)
                            .background(Color(0xFFEDF4FC), shape = CircleShape)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = item.price,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.Black
                )
            }
        }
    }
}

data class ActivityItem(
    val title: String,
    val description: String,
    val date: String,
    val price: String,
    val backgroundColor: Color,
    val isDone: Boolean
)
