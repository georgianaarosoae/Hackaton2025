package com.example.hackaton2025.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.example.hackaton2025.R
import com.example.hackaton2025.data.fakeData.WishlistData
import com.example.hackaton2025.wishlist.WishlistItem
import com.example.hackaton2025.wishlist.distribuiBugetulPePrioritati

@Composable
fun MainObjectives() {
    val sampleItems = listOf(
        WishlistData("Playstation", 2000f, R.drawable.playstation, 1),
        WishlistData("Bicycle", 1000f, R.drawable.bike, 2),
        WishlistData("Shoes", 500f, R.drawable.shoe, 3),
    )

    var savedAmount by remember { mutableStateOf(600f) }
    var wishlist by remember { mutableStateOf(sampleItems.toMutableList()) }

    val alocari = remember(wishlist, savedAmount) {
        distribuiBugetulPePrioritati(wishlist, savedAmount)
    }

    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp.dp
    val itemSize = screenWidthDp * 0.35f

    LazyRow(
        contentPadding = PaddingValues(vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        itemsIndexed(wishlist, key = { _, item -> item.name }) { index, item ->

            Box(
                modifier = Modifier
                    .width(itemSize),
                contentAlignment = Alignment.Center
            ) {
                WishlistItem(
                    item = item,
                    alocat = alocari[item.name] ?: 0f,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(240.dp)
                )
            }
        }
    }
}