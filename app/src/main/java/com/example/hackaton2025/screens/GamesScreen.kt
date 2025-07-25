package com.example.hackaton2025.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.navigation.NavController
import com.example.hackaton2025.R
import com.example.hackaton2025.MainGamesScreen

@Composable
fun GamesScreen(navController: NavController) {
    Box(modifier = Modifier.fillMaxSize().background(Color(0xFFFFFFFF))) {
        MainGamesScreen(modifier = Modifier, navController)
    }
}