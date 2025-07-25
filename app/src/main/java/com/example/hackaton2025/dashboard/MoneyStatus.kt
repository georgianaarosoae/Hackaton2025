package com.example.hackaton2025.dashboard

import android.graphics.Paint
import android.graphics.PathMeasure
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import java.time.format.DateTimeFormatter

@Composable
fun MoneyStatusScreen(
    profitAmount: String = "€ 90",
    percentageChange: String = "+15%",
    description: String = "From the previous week"
) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val cardHeight = screenHeight * 0.40f

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
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
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

            Spacer(Modifier.height(16.dp))

            AnimatedSmoothLine(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f) 
            )
        }
    }
}

@Composable
fun AnimatedSmoothLine(
    modifier: Modifier = Modifier,
    animationDuration: Int = 2000
) {
    val balanceHistory = listOf(
        Pair("2025-02-15T00:00:00Z", 100),
        Pair("2025-03-23T00:00:00Z", 50),
        Pair("2025-04-20T00:00:00Z", 150),
        Pair("2025-05-30T00:00:00Z", 243),
        Pair("2025-06-11T00:00:00Z", 300),
        Pair("2025-07-25T00:00:00Z", 250)
    )

    var startAnimation by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        startAnimation = true
    }

    val progress by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(durationMillis = animationDuration, easing = LinearEasing),
        label = "GraphAnimation"
    )

    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(160.dp)
    ) {
        val width = size.width
        val height = size.height

        val verticalPadding = 0.dp.toPx()

        val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME
        val timestamps = balanceHistory.map {
            java.time.ZonedDateTime.parse(it.first, formatter).toEpochSecond()
        }
        val minTimestamp = timestamps.minOrNull()!!
        val maxTimestamp = timestamps.maxOrNull()!!

        val minValue = balanceHistory.minOf { it.second }
        val maxValue = balanceHistory.maxOf { it.second }

        val points = balanceHistory.mapIndexed { index, (_, value) ->
            val timestamp = timestamps[index]
            val xRatio = (timestamp - minTimestamp).toFloat() / (maxTimestamp - minTimestamp).toFloat()
            val x = xRatio * width

            val valueRatio = (value - minValue).toFloat() / (maxValue - minValue).toFloat()
            val y = verticalPadding + (1 - valueRatio) * (height - 2 * verticalPadding)

            Offset(x, y)
        }


        val path = Path().apply {
            if (points.isNotEmpty()) {
                moveTo(points[0].x, points[0].y)
                for (i in 0 until points.size - 1) {
                    val midPoint = Offset(
                        (points[i].x + points[i + 1].x) / 2f,
                        (points[i].y + points[i + 1].y) / 2f
                    )
                    quadraticBezierTo(points[i].x, points[i].y, midPoint.x, midPoint.y)
                }
                val secondLast = points[points.size - 2]
                val last = points.last()
                quadraticBezierTo(secondLast.x, secondLast.y, last.x, last.y)
            }
        }

        val fillPath = Path().apply {
            addPath(path)
            if (points.isNotEmpty()) {
                lineTo(points.last().x, height)
                lineTo(points.first().x, height)
                close()
            }
        }

        val fillBrush = Brush.verticalGradient(
            colors = listOf(Color.White.copy(alpha = 0.3f), Color.Transparent)
        )


        val pathLengthEstimate = width * 1.2f
        val effect = PathEffect.dashPathEffect(
            floatArrayOf(pathLengthEstimate, pathLengthEstimate),
            phase = pathLengthEstimate * (1 - progress)
        )

        drawPath(
            path = fillPath,
            brush = fillBrush,
            alpha = progress
        )
        drawPath(
            path = path,
            color = Color.White,
            style = Stroke(
                width = 4.dp.toPx(),
                cap = StrokeCap.Round,
                join = StrokeJoin.Round,
                pathEffect = effect
            )
        )
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