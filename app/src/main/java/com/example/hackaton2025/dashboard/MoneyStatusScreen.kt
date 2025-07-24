package com.example.hackaton2025.dashboard

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun MoneyStatusScreen(
    profitAmount: String = "$ 15.2",
    percentageChange: String = "+15%",
    description: String = "From the previous week"
) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val cardHeight = screenHeight * 0.30f

    val animationProgress = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        delay(300)
        animationProgress.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 1200, easing = FastOutSlowInEasing)
        )
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(cardHeight)
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(Color(0xFF062B44), Color(0xFF0D5688))
                ),
                shape = RoundedCornerShape(16.dp)
            )
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp)
        ) {
            Text(
                text = "Money status",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal
            )

            Spacer(Modifier.height(24.dp))

            Text(
                text = profitAmount,
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(
                    modifier = Modifier
                        .background(Color(0xFFB0F2B4), shape = RoundedCornerShape(50))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = percentageChange,
                        color = Color(0xFF0C7C10),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = description,
                    color = Color.White.copy(alpha = 0.9f),
                    fontSize = 12.sp
                )
            }
        }

        Canvas(
            modifier = Modifier
                .fillMaxSize()
        ) {
            val width = size.width
            val height = size.height

            val points = listOf(
                Offset(0f, height * 0.7f),
                Offset(width * 0.3f, height * 0.5f),
                Offset(width * 0.6f, height * 0.8f),
                Offset(width, height * 0.6f)
            )

            val lineColor = Color.White.copy(alpha = 0.8f)
            val fillColor = Brush.verticalGradient(
                colors = listOf(
                    Color.White.copy(alpha = 0.3f),
                    Color.Transparent
                )
            )

            val progress = animationProgress.value

            val path = Path()
            var started = false
            var lastPoint: Offset? = null

            for (i in 0 until points.size - 1) {
                val start = points[i]
                val end = points[i + 1]
                val segmentProgress = (progress * (points.size - 1)) - i

                if (segmentProgress <= 0f) break

                val clampedProgress = segmentProgress.coerceIn(0f, 1f)
                val currentEnd = Offset(
                    x = start.x + (end.x - start.x) * clampedProgress,
                    y = start.y + (end.y - start.y) * clampedProgress
                )

                if (!started) {
                    path.moveTo(start.x, start.y)
                    started = true
                }
                path.lineTo(currentEnd.x, currentEnd.y)

                lastPoint = currentEnd

                if (segmentProgress < 1f) break
            }

            if (started && lastPoint != null) {

                path.lineTo(lastPoint.x, height)
                path.lineTo(points.first().x, height)
                path.close()

                drawPath(
                    path = path,
                    brush = fillColor
                )
            }

            started = false
            for (i in 0 until points.size - 1) {
                val start = points[i]
                val end = points[i + 1]
                val segmentProgress = (progress * (points.size - 1)) - i

                if (segmentProgress <= 0f) continue

                val clampedProgress = segmentProgress.coerceIn(0f, 1f)
                val currentEnd = Offset(
                    x = start.x + (end.x - start.x) * clampedProgress,
                    y = start.y + (end.y - start.y) * clampedProgress
                )

                drawLine(
                    color = lineColor,
                    start = start,
                    end = currentEnd,
                    strokeWidth = 3f
                )

                if (segmentProgress < 1f) break
            }

            if (progress * (points.size - 1) >= 2) {
                drawCircle(
                    color = Color.White,
                    radius = 8f,
                    center = points[2]
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MoneyStatusScreenPreview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        MoneyStatusScreen()
    }
}