package com.example.hackaton2025

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.hackaton2025.ui.theme.Hackaton2025Theme
@Composable
fun GameStartScreen(
    gameName: String,
    navController: NavController? = null,
    onClose: () -> Unit = {}
) {
    val rocketOffset by rememberInfiniteTransition().animateFloat(
        initialValue = 0f,
        targetValue = -100f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    val planetOffset by rememberInfiniteTransition().animateFloat(
        initialValue = -8f,
        targetValue = 8f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(32.dp),
            modifier = Modifier.align(Alignment.Center)
        ) {
            // Floating planets
            Text(
                text = "🪐 🛸 🌍",
                fontSize = 36.sp,
                modifier = Modifier.offset(y = planetOffset.dp)
            )

            // Main title
            Text(
                text = "The game \"$gameName\" is about to begin!\nAre you ready? 🚀",
                fontWeight = FontWeight.W400,
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center
            )

            // Rocket animation
            Text(
                text = "🚀",
                fontSize = 64.sp,
                modifier = Modifier.offset(y = rocketOffset.dp)
            )
            Spacer(Modifier.height(10.dp))

            // Vertical buttons
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth(0.6f)
            ) {
                Button(
                    onClick = { navController?.navigate("memory_game") },
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF182883), // Fundalul butonului
                        contentColor = Color.White          // Culoarea textului/iconiței
                    )
                ) {
                    Text("Start")
                }

                OutlinedButton(
                    onClick = onClose,
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = Color.White,
                        contentColor = Color(0xFF182883)
                    )
                ) {
                    Text("Close")
                }

            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun GameStartScreenPreview() {
    Hackaton2025Theme {
        GameStartScreen(gameName = "Memory")
    }
}
