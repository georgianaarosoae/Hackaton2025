package com.example.hackaton2025.wishlist

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.hackaton2025.R
import org.burnoutcrew.reorderable.*
import kotlin.math.min

@Composable
fun WishlistScreen( navController: NavController) {

    //lista de wish list ( 3 obiecte )
    val sampleItems = listOf(
        WishlistItem("Shoes", 500f, R.drawable.shoe, 1),
        WishlistItem("Lego", 300f, R.drawable.lego, 2),
        WishlistItem("Bicycle", 1000f, R.drawable.bike, 3),
        WishlistItem("Playstation", 2000f, R.drawable.playstation, 4),
        WishlistItem("Wireless Headphones", 500f, R.drawable.headphones, 5),
        WishlistItem("Karaoke Microphone", 90f, R.drawable.microphone, 6),
        WishlistItem("Board Game", 100f, R.drawable.board_game, 7),
        WishlistItem("Game Controller", 250f, R.drawable.controller, 8),
    )

    var wishlist by remember { mutableStateOf(sampleItems.toMutableList()) }
    var savedAmount by remember { mutableStateOf(2000f) }
    var showDialog by remember { mutableStateOf(false) }

    val reorderState = rememberReorderableLazyListState(
        onMove = { from, to ->
            wishlist = wishlist.toMutableList().apply {
                add(to.index, removeAt(from.index))

                //  Actualizăm prioritățile după mutare
                forEachIndexed { index, item ->
                    this[index] = item.copy(prioriry = index + 1)
                }
            }
        }
    )

    val alocari = remember(wishlist, savedAmount) {
        distribuiBugetulPePrioritati(wishlist, savedAmount)
    }

    Box(modifier = Modifier.fillMaxSize()){
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)) {

            // Total economisit
            Text(
                text = "Saved ${savedAmount.toInt()} DB Coins",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF062B44),
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp)
                    .statusBarsPadding()
            )

            // Lista obiecte
            LazyColumn(
                state = reorderState.listState,
                modifier = Modifier
                    .weight(1f)
                    .reorderable(reorderState)
                    .detectReorderAfterLongPress(reorderState)
            ) {
                itemsIndexed(wishlist, key = { _, item -> item.name }) { index, item ->
                    ReorderableItem(reorderState, key = item.name) { isDragging ->
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 12.dp)
                        ) {
                            WishlistItem(
                                item = item,
                                alocat = alocari[item.name] ?: 0f,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 10.dp)
                            )
                        }
                    }
                }
            }
        }
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            FloatingActionButton(
                onClick = { showDialog = true },
                shape = CircleShape,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Adaugă obiect"
                )
            }
        }
        if (showDialog) {
            AddItemDialog(
                onAdd = { newItem ->
                    val itemWithImage = newItem.copy(imageRes = R.drawable.present)
                    wishlist.add(itemWithImage)
                    wishlist = wishlist.mapIndexed { index, item ->
                        item.copy(prioriry = index + 1)
                    }.toMutableList()
                    showDialog = false
                },
                onCancel = { showDialog = false }
            )
        }
    }
}

data class WishlistItem(
    val name: String,
    val price: Float,
    val imageRes: Int,
    val prioriry:Int // setare prioritate
)

fun distribuiBugetulPePrioritati(
    items: List<WishlistItem>,
    totalEconomisit: Float
): Map<String, Float> {
    val sortedItems = items.sortedBy { it.prioriry }
    val alocari = mutableMapOf<String, Float>()

    var bugetRamas = totalEconomisit

    for (item in sortedItems) {
        val sumaAlocata = min(item.price, bugetRamas)
        alocari[item.name] = sumaAlocata
        bugetRamas -= sumaAlocata

        if (bugetRamas <= 0f) break
    }

    return alocari
}



