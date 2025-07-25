package com.example.hackaton2025

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.hackaton2025.navigation.Destinations
import com.example.hackaton2025.screens.GamesScreen
import com.example.hackaton2025.ui.theme.Hackaton2025Theme

@Composable
fun MainGamesScreen(modifier: Modifier = Modifier, navController: NavController) {
    val games = listOf("Memory", "Hangman", "Puzzle", "Quiz")

    val gameData = mapOf(
        "Memory" to (R.drawable.memory to "Memorize and find matching pairs!"),
        "Hangman" to (R.drawable.riddle to "Guess the words before it’s too late!"),
        "Puzzle" to (R.drawable.puzzle253 to "Complete the puzzle as fast as you can!"),
        "Quiz" to (R.drawable.quizpng to "Test your general knowledge!")
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Choose a game 🎮",
            fontFamily = FontFamily(
                Font(R.font.minecraft_font)
            ),
            fontWeight = FontWeight.W800,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(vertical = 16.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(24.dp),
            contentPadding = PaddingValues(bottom = 24.dp)
        ) {
            items(games) { game ->
                val (iconRes, description) = gameData[game] ?: (R.drawable.outline_airplane_ticket_24 to "No description")

                GameCard(
                    title = game,
                    timesPlayed = game.length,
                    iconResId = iconRes,
                    description = description
                ) {
                    when (game) {
                        "Hangman" -> navController.navigate(Destinations.HangManDestionation)
                        else -> navController.navigate(Destinations.MemoryGameDestination)
                    }
                }
            }
        }
    }
}

@Composable
fun GameCard(
    title: String,
    timesPlayed: Int,
    iconResId: Int,
    description: String,
    onClick: () -> Unit
) {
    val backgroundColor = randomPastelColor()
    val darkerColor = backgroundColor.copy(alpha = 0.85f).compositeOver(Color.Black)

    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(24.dp),
                clip = false
            )
            .clip(RoundedCornerShape(24.dp)),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = iconResId),
                    contentDescription = "$title Icon",
                    modifier = Modifier.size(60.dp)
                )

                Spacer(modifier = Modifier.width(12.dp))

                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Column(
                        modifier = Modifier.padding(start = 16.dp)
                    ) {
                        Text(
                            text = title,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily(Font(R.font.minecraft_font)),
                            style = MaterialTheme.typography.headlineSmall,
                            color = Color.White
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = description,
                            fontWeight = FontWeight.Normal,
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.White
                        )
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(36.dp)
                    .background(
                        color = darkerColor,
                        shape = RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp)
                    )
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = " $timesPlayed PLAYS",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White
                )
            }
        }
    }
}

fun randomPastelColor(): Color {
    val lightBlueNavyShades = listOf(
        Color(0xFF3B5998),
        Color(0xFF4169E1),
        Color(0xFF5B84B1),
        Color(0xFF4A90E2),
        Color(0xFF6C8CD5),
        Color(0xFF7BAAF7),
        Color(0xFF5271FF)
    )
    return lightBlueNavyShades.random()
}

@Composable
@Preview(showBackground = true)
fun GamesScreenPreview() {
    Hackaton2025Theme {
        GamesScreen(navController = rememberNavController())
    }
}

