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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.hackaton2025.Constants
import com.example.hackaton2025.R
import com.example.hackaton2025.data.fakeData.WishlistData
import org.burnoutcrew.reorderable.*
import kotlin.math.min

@Composable
fun WishlistScreen( navController: NavController) {

    val sampleItems = listOf(       // TODO eliminina
        WishlistData("Lego", 40f, R.drawable.lego, 2),
        WishlistData("Bicycle", 600f, R.drawable.bike, 3),
        WishlistData("New Headphones", 70f, R.drawable.headphones, 5),
        WishlistData("Board Game", 20f, R.drawable.board_game, 7),
        WishlistData("Game Controller", 75f, R.drawable.controller, 8),
    )

    var wishlist by remember { mutableStateOf(sampleItems.toMutableList()) }
    var savedAmount by remember { mutableStateOf(120f) }
    var showDialog by remember { mutableStateOf(false) }

    val reorderState = rememberReorderableLazyListState(
        onMove = { from, to ->
            wishlist = wishlist.toMutableList().apply {
                add(to.index, removeAt(from.index))
                forEachIndexed { index, item ->
                    this[index] = item.copy(priority = index + 1)
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
            Text(
                text = "Wishlist",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF062B44),
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = stringResource(R.string.whishlist_title, savedAmount.toInt().toString(), Constants.CURRENCY_SIGN),
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF062B44),
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

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
                    contentDescription = stringResource(id = R.string.whishlist_add_a_wishilist_item),
                )
            }
        }
        if (showDialog) {
            AddItemDialog(
                onAdd = { newItem ->
                    val itemWithImage = newItem.copy(imageRes = R.drawable.present)
                    wishlist.add(itemWithImage)
                    wishlist = wishlist.mapIndexed { index, item ->
                        item.copy(priority = index + 1)
                    }.toMutableList()
                    showDialog = false
                },
                onCancel = { showDialog = false }
            )
        }
    }
}

fun distribuiBugetulPePrioritati(
    items: List<WishlistData>,
    totalEconomisit: Float
): Map<String, Float> {
    val sortedItems = items.sortedBy { it.priority }
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



