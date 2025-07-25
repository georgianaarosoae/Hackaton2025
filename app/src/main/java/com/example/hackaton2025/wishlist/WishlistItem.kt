package com.example.hackaton2025.wishlist

import android.util.Log
import androidx.annotation.ColorRes
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.hackaton2025.Constants
import com.example.hackaton2025.R
import com.example.hackaton2025.data.fakeData.WishlistData
import kotlin.math.roundToInt


@Composable
fun WishlistItem(
    wishlistItem: WishlistData,
    alocat: Float,
    modifier: Modifier = Modifier,
    showLoanButton: Boolean = true
) {
    val progress = (alocat / wishlistItem.price).coerceIn(0f, 1f)
    val progressPercent = (progress * 100).roundToInt()

    var showLoanDialog by remember { mutableStateOf(false) }
    var requestedLoanAmount by remember { mutableStateOf("") }

    Card(
        modifier = modifier
            .height(200.dp)
            .clip(RoundedCornerShape(24.dp))
            .shadow(8.dp),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 15.dp
        ),
    ) {
        BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
            val density = LocalDensity.current
            
            var animatedTargetWidthDp by remember { mutableStateOf(0.dp) }
            val currentProgressWidthDp by animateDpAsState(
                targetValue = animatedTargetWidthDp, // Animate towards the dynamically set target
                animationSpec = tween(
                    durationMillis = 1000,
                    easing = FastOutSlowInEasing
                ),
                finishedListener = {

                }
            )

            val fullWidthPx = constraints.maxWidth.toFloat()
            val finalProgressFactor = (alocat / wishlistItem.price).coerceIn(0f, 1f)
            val targetWidthInDpForThisItem = with(density) { (fullWidthPx * finalProgressFactor).toDp() }
            LaunchedEffect(targetWidthInDpForThisItem) {
                animatedTargetWidthDp = targetWidthInDpForThisItem
            }

            Box(
                modifier = Modifier
                    .matchParentSize()
                    .backgroundResourceColor(R.color.odysseyBlue300)) //completat                    )
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(currentProgressWidthDp)
                    .backgroundResourceColor(R.color.odysseyBlue900) //necompletat
            )
            Log.e("wish", "progressWidthDp: $currentProgressWidthDp")

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(), contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = wishlistItem.imageRes),
                        contentDescription = wishlistItem.name,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .width(150.dp)
                            .height(100.dp)
                            .shadow(16.dp)
                            .clip(RoundedCornerShape(24.dp))
                            .background(Color.White)
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Column {
                        Text(
                            text = wishlistItem.name,
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = Color.White
                        )
                        Text(
                            text = "${Constants.CURRENCY_SIGN} ${alocat.toInt()} / ${wishlistItem.price.toInt()}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White
                        )
                    }

                    Text(
                        text = "$progressPercent%",
                        style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.SemiBold),
                        color = Color.White
                    )

                    if (progress < 1f && showLoanButton) {
                        Button(
                            onClick = { showLoanDialog = true },
                            modifier = Modifier
                                .padding(top = 8.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1E2A78))
                        ) {
                            Text("Request Loan", color = Color.White)
                        }
                    }
                }
            }
        }
    }

    if (showLoanDialog) {
        AlertDialog(
            onDismissRequest = { showLoanDialog = false },
            title = { Text("Request Loan for ${wishlistItem.name}") },
            text = {
                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(bottom = 8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = "Info",
                            tint = Color.Gray,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Ask your parent to help complete the savings.",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Gray
                        )
                    }

                    OutlinedTextField(
                        value = requestedLoanAmount,
                        onValueChange = { requestedLoanAmount = it },
                        label = { Text(stringResource(R.string.whishlist_amount, Constants.CURRENCY_NAME)) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Remaining:${(wishlistItem.price - alocat).toInt()} ${Constants.CURRENCY_NAME}")
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    showLoanDialog = false
                    requestedLoanAmount = ""
                }) {
                    Text("Send")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showLoanDialog = false
                    requestedLoanAmount = ""
                }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
fun Modifier.backgroundResourceColor(@ColorRes colorResId: Int): Modifier {
    val color = colorResource(id = colorResId)
    return this.background(color)
}




