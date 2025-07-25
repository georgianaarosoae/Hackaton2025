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

@Composable
fun MoneyStatusScreen(
    profitAmount: String = "$ 15.2",
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

        AnimatedSmoothLine()
    }
}

@Composable
fun AnimatedSmoothLine(
    modifier: Modifier = Modifier,
    animationDuration: Int = 2000
) {
    var startAnimation by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        startAnimation = true
    }

    val progress by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(durationMillis = animationDuration, easing = LinearEasing),
        label = "GraphAnimation"
    )

    Column(modifier = modifier.padding(top = 10.dp)) {
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
        ) {
            val width = size.width
            val height = size.height

            // ✅ Make sure the last point is really at the end
            val points = listOf(
                Offset(0f, height * 0.7f),
                Offset(width * 0.3f, height * 0.5f),
                Offset(width * 0.6f, height * 0.8f),
                Offset(width, height * 0.6f)   // last point at the end
            )

            val path = Path().apply {
                moveTo(points[0].x, points[0].y)
                for (i in 0 until points.size - 1) {
                    val midPoint = Offset(
                        (points[i].x + points[i + 1].x) / 2,
                        (points[i].y + points[i + 1].y) / 2
                    )
                    quadraticBezierTo(points[i].x, points[i].y, midPoint.x, midPoint.y)
                }
                // ✅ finally, curve to the last point to make sure the line goes to the end
                val secondLast = points[points.size - 2]
                val last = points.last()
                quadraticBezierTo(secondLast.x, secondLast.y, last.x, last.y)
            }

            // Fill area under the curve
            val fillPath = Path().apply {
                addPath(path)
                lineTo(points.last().x, height)
                lineTo(points.first().x, height)
                close()
            }

            val fillBrush = Brush.verticalGradient(
                colors = listOf(Color.White.copy(alpha = 0.3f), Color.Transparent)
            )

            // Dash effect: estimate length better (length is close to width*1.2 because of curve)
            val pathLengthEstimate = width * 1.2f
            val effect = PathEffect.dashPathEffect(
                floatArrayOf(pathLengthEstimate, pathLengthEstimate),
                phase = pathLengthEstimate * (1 - progress)
            )

            // Draw animated fill
            drawPath(
                path = fillPath,
                brush = fillBrush,
                alpha = progress
            )

            // Draw animated white line with rounded corners
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