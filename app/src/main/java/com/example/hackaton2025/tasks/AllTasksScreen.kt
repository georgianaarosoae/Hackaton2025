package com.example.hackaton2025.tasks

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.hackaton2025.data.fakeData.FakeData

@Composable
fun AllTasksScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        NavigationRow()

        WeeklyStatusRow()

        Text(
            text = "Tasks",
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold,
                color = Color(0xFF062B44),
            ),
            textAlign = TextAlign.Start,
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            items(FakeData.allTasks) { item ->
                Column {
                    TaskCard(item)
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }
}

@Composable
fun NavigationRow(
    title: String = "Today",
    onLeftClick: () -> Unit = {},
    onRightClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(onClick = onLeftClick) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Previous",
                tint = Color.Black
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF062B44)
                )
            )
            Text(
                text = "27 Jul, 2025",
                fontSize = 16.sp,
                color = Color.Black,
                fontWeight = FontWeight.Medium
            )
        }

        IconButton(onClick = onRightClick) {
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = "Next",
                tint = Color.Black
            )
        }
    }
}


@Composable
fun WeeklyStatusRow() {
    val days = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
    val randomProgress = listOf(0.7f, 0.4f, 1f, 0.6f, 0.8f, 0f, 0f)
    val activeColor = Color(0xFF0D5688)
    val remainingColor = Color.LightGray
    val backgroundColor = Color.White
    val fridayBoxColor = Color(0xFFE0E0E0)

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        days.forEachIndexed { index, day ->
            val isWeekend = day == "Sat" || day == "Sun"
            val isFriday = day == "Fri"

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = if (isFriday) {
                    Modifier
                        .background(fridayBoxColor, shape = RoundedCornerShape(8.dp))
                        .padding(horizontal = 8.dp, vertical = 6.dp)
                } else Modifier
            ) {
                StatusCircle(
                    progress = randomProgress[index],
                    isWeekend = isWeekend,
                    activeColor = activeColor,
                    remainingColor = remainingColor,
                    backgroundColor = backgroundColor
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = day,
                    fontSize = 12.sp,
                    color = Color.Black
                )
            }
        }
    }
}

@Composable
fun StatusCircle(
    progress: Float,
    isWeekend: Boolean,
    activeColor: Color,
    remainingColor: Color,
    backgroundColor: Color,
    size: Dp = 32.dp,
    strokeWidth: Dp = 4.dp
) {
    Canvas(modifier = Modifier.size(size)) {
        val sweepAngle = progress * 360f
        val diameter = size.toPx()
        val stroke = strokeWidth.toPx()

        if (isWeekend) {
            drawArc(
                color = remainingColor,
                startAngle = 0f,
                sweepAngle = 360f,
                useCenter = false,
                style = Stroke(width = stroke, cap = StrokeCap.Round)
            )
        } else {
            drawArc(
                color = remainingColor,
                startAngle = 0f,
                sweepAngle = 360f,
                useCenter = false,
                style = Stroke(width = stroke, cap = StrokeCap.Round)
            )
            drawArc(
                color = activeColor,
                startAngle = -90f,
                sweepAngle = sweepAngle,
                useCenter = false,
                style = Stroke(width = stroke, cap = StrokeCap.Round)
            )
        }

        drawCircle(
            color = backgroundColor,
            radius = (diameter / 2f) - (stroke / 2f)
        )
    }
}


@Preview(showBackground = true)
@Composable
fun AllTasksScreenPreview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        AllTasksScreen(rememberNavController())
    }
}