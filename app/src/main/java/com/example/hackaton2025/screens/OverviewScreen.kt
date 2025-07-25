package com.example.hackaton2025.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.hackaton2025.R
import com.example.hackaton2025.dashboard.DashboardScreen

@Composable
fun OverviewScreen(navController: NavController) {
        DashboardScreen(navController)
}