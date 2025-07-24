package com.example.hackaton2025.wishlist

import androidx.annotation.ColorRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.roundToInt
import androidx.compose.ui.res.colorResource
import com.example.hackaton2025.R


@Composable
fun WishlistItem(item: WishlistItem, alocat: Float, modifier: Modifier = Modifier) {
    val progress = (alocat / item.price).coerceIn(0f, 1f)
    val progressPercent = (progress * 100).roundToInt()

    Card(
        modifier = modifier
            .height(200.dp)
            .clip(RoundedCornerShape(24.dp))
            .shadow(8.dp),
        shape = RoundedCornerShape(24.dp)
    ) {
        BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
            val fullWidth = constraints.maxWidth.toFloat()
            val progressWidth = fullWidth * progress

            Box(modifier = Modifier.fillMaxSize()) {
                Row(modifier = Modifier.fillMaxSize()) {
                    Box(
                        modifier = Modifier
                            .width(progressWidth.dp)
                            .fillMaxHeight()
                            .backgroundResourceColor(R.color.odysseyBlue900)) //completat                    )
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .backgroundResourceColor(R.color.odysseyBlue300) //necompletat
                    )
                }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth() ,contentAlignment = Alignment.Center) {
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
                                text = "RON ${alocat.toInt()} / ${item.price.toInt()}",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.White
                            )
                        }

                        Text(
                            text = "$progressPercent%",
                            style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.SemiBold),
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Modifier.backgroundResourceColor(@ColorRes colorResId: Int): Modifier {
    val color = colorResource(id = colorResId)
    return this.background(color)
}




