package com.example.hackaton2025

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.hackaton2025.ui.theme.Hackaton2025Theme
import kotlinx.coroutines.delay

data class MemoryCard(
    val emoji: String,
    var isFaceUp: Boolean = false,
    var isMatched: Boolean = false
)

@Composable
fun MemoryGameScreen(
    navController: NavController? = null
) {
    var cards by remember { mutableStateOf(generateShuffledCards()) }
    var firstCardIndex by remember { mutableStateOf<Int?>(null) }
    var isProcessing by remember { mutableStateOf(false) }
    var triggerFlipBack by remember { mutableStateOf(false) }
    var flippedIndexes by remember { mutableStateOf(Pair(-1, -1)) }
    var showCongratsDialog by remember { mutableStateOf(false) }
    var coins by remember { mutableStateOf(0) }

    val allMatched = cards.all { it.isMatched }
    if (allMatched && !showCongratsDialog) {
        showCongratsDialog = true
        coins += 1
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF16184E))
            .padding(16.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
        ) {
            Row() {
                Spacer(Modifier.weight(1f))
                IconButton(
                    onClick = { navController?.popBackStack() },
                    modifier = Modifier.offset(16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                        tint = Color.White
                    )
                }
            }


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(0.dp, -40.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.pngtree_saving_money_clipart_boy_giving_piggy_bank_coins_to_save_money_vector_png_image_6866400),
                    contentDescription = "Logo",
                    modifier = Modifier.size(80.dp)
                )

                Text(
                    text = "Match the emojis",
                    fontSize = 26.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )

            }


            LazyVerticalGrid(
                columns = GridCells.Fixed(4),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .weight(1f)
                    .offset(
                        0.dp, -32.dp
                    )
            ) {
                itemsIndexed(cards) { index, card ->
                    CardFlip(
                        card = card,
                        onClick = {
                            if (isProcessing || card.isFaceUp || card.isMatched) return@CardFlip

                            cards = cards.toMutableList().also {
                                it[index] = it[index].copy(isFaceUp = true)
                            }

                            if (firstCardIndex == null) {
                                firstCardIndex = index
                            } else {
                                isProcessing = true
                                val secondCardIndex = index
                                val firstCard = cards[firstCardIndex!!]

                                if (firstCard.emoji == cards[secondCardIndex].emoji) {
                                    cards = cards.toMutableList().also {
                                        it[firstCardIndex!!] =
                                            it[firstCardIndex!!].copy(isMatched = true)
                                        it[secondCardIndex] =
                                            it[secondCardIndex].copy(isMatched = true)
                                    }
                                    firstCardIndex = null
                                    isProcessing = false
                                } else {
                                    flippedIndexes = Pair(firstCardIndex!!, secondCardIndex)
                                    triggerFlipBack = true
                                    firstCardIndex = null
                                }
                            }
                        }
                    )
                }
            }
        }

        if (triggerFlipBack) {
            LaunchedEffect(triggerFlipBack) {
                delay(1000)
                cards = cards.toMutableList().also {
                    val (first, second) = flippedIndexes
                    it[first] = it[first].copy(isFaceUp = false)
                    it[second] = it[second].copy(isFaceUp = false)
                }
                isProcessing = false
                triggerFlipBack = false
            }
        }

        if (showCongratsDialog) {
            Box(
                Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.4f))
                    .blur(8.dp)
            )

            Surface(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(32.dp),
                shape = MaterialTheme.shapes.medium,
                color = Color.White,
                tonalElevation = 8.dp
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        "🎉 Great Job!",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF2E7D32),
                        textAlign = TextAlign.Center
                    )
                    Text(
                        "You've matched all the cards!",
                        textAlign = TextAlign.Center
                    )
                    Button(
                        onClick = { showCongratsDialog = false },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Close")
                    }
                }
            }
        }
    }
}

@Composable
fun CardFlip(card: MemoryCard, onClick: () -> Unit) {
    val rotation by animateFloatAsState(
        targetValue = if (card.isFaceUp || card.isMatched) 0f else 180f,
        animationSpec = androidx.compose.animation.core.tween(durationMillis = 400)
    )

    Box(
        modifier = Modifier
            .aspectRatio(0.8f)
            .clipToBounds()
            .graphicsLayer {
                rotationY = rotation
                cameraDistance = 12 * density
            }
            .clickable(enabled = !card.isFaceUp && !card.isMatched) { onClick() }
            .background(
                if (card.isFaceUp || card.isMatched) Color.White else Color(0xFF1C266F),
                shape = MaterialTheme.shapes.medium
            )
            .padding(4.dp),
        contentAlignment = Alignment.Center
    ) {
        if (rotation <= 90f) {
            Text(
                text = card.emoji,
                fontSize = 32.sp
            )
        }
    }
}

fun generateShuffledCards(): List<MemoryCard> {
    val emojis = listOf("💰", "💳", "💵", "💷", "🪙", "🏦", "💷", "💴")
    val pairs = (emojis + emojis).shuffled()
    return pairs.map { MemoryCard(it) }
}

@Preview(showBackground = true)
@Composable
fun MemoryGameScreenPreview() {
    Hackaton2025Theme {
        MemoryGameScreen()
    }
}
