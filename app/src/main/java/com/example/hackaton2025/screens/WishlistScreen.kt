package com.example.hackaton2025.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.navigation.NavController
import com.example.hackaton2025.R

@Composable
fun WishlistScreen(navController: NavController) {
    Box(modifier = Modifier.fillMaxSize().background(colorResource(R.color.additional200)))
}