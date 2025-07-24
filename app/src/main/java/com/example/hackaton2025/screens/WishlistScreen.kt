package com.example.hackaton2025.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController

@Composable
fun WishlistScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .background(Color(0xFFFFFFFF)).
            fillMaxSize()
    )
    {
        com.example.hackaton2025.wishlist.WishlistScreen(modifier = Modifier.navigationBarsPadding())
    }
}