package com.example.hackaton2025.wishlist

import androidx.annotation.ColorRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import com.example.hackaton2025.Constants
import com.example.hackaton2025.R
import com.example.hackaton2025.data.fakeData.WishlistData


@Composable
fun WishlistItem(item: WishlistData, alocat: Float, modifier: Modifier = Modifier) {
    val progress = (alocat / item.price).coerceIn(0f, 1f)
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
            val fullWidth = constraints.maxWidth.toFloat()
            val progressWidth = fullWidth * progress
            val progressWidthDp = with(density) { progressWidth.toDp() }

                    Box(
                        modifier = Modifier
                            .matchParentSize()
                            .backgroundResourceColor(R.color.odysseyBlue300)) //completat                    )
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(progressWidthDp)
                            .backgroundResourceColor(R.color.odysseyBlue900) //necompletat
                    )

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
                            painter = painterResource(id = item.imageRes),
                            contentDescription = item.name,
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
                                text = item.name,
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = Color.White
                            )
                            Text(
                                text = "${Constants.CURRENCY_SIGN} ${alocat.toInt()} / ${item.price.toInt()}",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.White
                            )
                        }

                        Text(
                            text = "$progressPercent%",
                            style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.SemiBold),
                            color = Color.White
                        )

                        if (progress < 1f) {
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
            title = { Text("Request Loan for ${item.name}") },
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
                    Text("Remaining:${(item.price - alocat).toInt()} ${Constants.CURRENCY_NAME}")
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




