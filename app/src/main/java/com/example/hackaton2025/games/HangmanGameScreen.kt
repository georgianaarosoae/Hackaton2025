package com.example.hackaton2025

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.hackaton2025.ui.theme.Hackaton2025Theme
import com.example.hackaton2025.R
import com.example.hackaton2025.navigation.Destinations

@Composable
fun HangmanGameScreen(
    question: String = "Which fruit keeps the doctor away?",
    answer: String = "APPLE",
    navController: NavController? = null
) {

    val scrollState = rememberScrollState()

    val maxAttempts = 6
    val answerUpper = answer.uppercase()
    var guessedLetters by remember { mutableStateOf(setOf<Char>()) }
    var attemptsLeft by remember { mutableStateOf(maxAttempts) }
    val displayWord = answerUpper.map { if (guessedLetters.contains(it)) it else '_' }.joinToString(" ")

    val gameWon = !displayWord.contains('_')
    val gameLost = attemptsLeft <= 0

    val darkBlue = colorResource(R.color.darkBlue)
    val blueButton = Color(0xFF182885)

    var showLostDialog by remember { mutableStateOf(false) }

    if (gameLost && !gameWon) {
        showLostDialog = true
    }

    if (showLostDialog) {
        AlertDialog(
            onDismissRequest = { },
            title = { Text("😢 You lost!") },
            text = { Text("The word was $answerUpper. Try again?") },
            confirmButton = {
                TextButton(onClick = {
                    showLostDialog = false
                    navController?.navigate(Destinations.HangManDestionation)
                }) {
                    Text("Yes")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showLostDialog = false
                    navController?.navigate(Destinations.GamesDestination)
                }) {
                    Text("No")
                }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(8.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Icon Back Button in top left
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, top = 8.dp),
            contentAlignment = Alignment.TopStart
        ) {
            IconButton(onClick = {
                navController?.popBackStack()
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_arrow_back_ios_24),
                    contentDescription = "Back",
                    tint = Color(0xFF182883)
                )
            }
        }

      //  Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "🪢HANGMAN GAME",
            color = Color(0xFF182883),
            fontSize = 23.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = question,
            color = darkBlue,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge
        )

        Text(
            text = displayWord,
            color = darkBlue,
            fontSize = 32.sp,
            letterSpacing = 4.sp
        )

        Text(
            text = "Attempts left: $attemptsLeft",
            color = darkBlue
        )

      //  Spacer(modifier = Modifier.height(40.dp)) // ⬅️ ADĂUGAT pentru spațiu

        HangmanWithBalloons(attemptsLeft = attemptsLeft, maxAttempts = maxAttempts)

        if (gameWon) {
            Text(
                text = "🎉 You won! The word was $answerUpper",
                color = Color(0xFF088824),
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        } else if (!gameLost) {
            LettersGrid(
                onLetterClick = { letter ->
                    if (!guessedLetters.contains(letter) && attemptsLeft > 0) {
                        guessedLetters = guessedLetters + letter
                        if (!answerUpper.contains(letter)) {
                            attemptsLeft--
                        }
                    }
                },
                usedLetters = guessedLetters,
                activeColor = blueButton,
                disabledColor = Color(0xFFE1E1E1)
            )
        }
    }
}

@Composable
fun HangmanWithBalloons(attemptsLeft: Int, maxAttempts: Int) {
    val mistakes = maxAttempts - attemptsLeft

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(360.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.colorful_balloons_for_a_holiday_surprise_bunch_of_helium_balloons_vector_illustration_in_cute),
            contentDescription = "Baloane colorate",
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth()
                .height(140.dp)
        )

        Canvas(modifier = Modifier.fillMaxSize()) {
            val centerX = size.width / 2f
            val balloonBottomY = 170f
            val hangmanTopY = 250f

            drawLine(
                color = Color.Gray,
                start = Offset(centerX, balloonBottomY),
                end = Offset(centerX, hangmanTopY),
                strokeWidth = 6f
            )

            if (mistakes >= 1) {
                drawCircle(Color.Black, radius = 30f, center = Offset(centerX, hangmanTopY + 30f))
            }
            if (mistakes >= 2) {
                drawLine(Color.Black, Offset(centerX, hangmanTopY + 60f), Offset(centerX, hangmanTopY + 140f), strokeWidth = 8f)
            }
            if (mistakes >= 3) {
                drawLine(Color.Black, Offset(centerX, hangmanTopY + 70f), Offset(centerX - 40f, hangmanTopY + 110f), strokeWidth = 8f)
            }
            if (mistakes >= 4) {
                drawLine(Color.Black, Offset(centerX, hangmanTopY + 70f), Offset(centerX + 40f, hangmanTopY + 110f), strokeWidth = 8f)
            }
            if (mistakes >= 5) {
                drawLine(Color.Black, Offset(centerX, hangmanTopY + 140f), Offset(centerX - 40f, hangmanTopY + 200f), strokeWidth = 8f)
            }
            if (mistakes >= 6) {
                drawLine(Color.Black, Offset(centerX, hangmanTopY + 140f), Offset(centerX + 40f, hangmanTopY + 200f), strokeWidth = 8f)
            }
        }
    }
}

@Composable
fun LettersGrid(
    onLetterClick: (Char) -> Unit,
    usedLetters: Set<Char>,
    activeColor: Color,
    disabledColor: Color,
) {
    val letters = ('A'..'Z').toList()
    val rows = letters.chunked(7)

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.offset(0.dp, -200.dp)
    ) {
        rows.forEach { row ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                row.forEach { letter ->
                    val isUsed = usedLetters.contains(letter)
                    Surface(
                        color = if (isUsed) disabledColor else activeColor,
                        shape = MaterialTheme.shapes.small,
                        modifier = Modifier
                            .size(40.dp)
                            .clickable(enabled = !isUsed) { onLetterClick(letter) }
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                text = letter.toString(),
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HangmanGamePreview() {
    Hackaton2025Theme {
        HangmanGameScreen("What is a bank?", "BANK", navController = null)
    }
}
