package com.example.hackaton2025.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.hackaton2025.R
import com.example.hackaton2025.viewmodel.OverviewViewModel

@Composable
fun OverviewScreen(navController: NavController) {
    val vm: OverviewViewModel = hiltViewModel()
    Box(modifier = Modifier.fillMaxSize().background(colorResource(R.color.red300)))
}