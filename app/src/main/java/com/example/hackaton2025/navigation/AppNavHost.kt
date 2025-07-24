package com.example.hackaton2025.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.hackaton2025.screens.GamesScreen
import com.example.hackaton2025.screens.OverviewScreen
import com.example.hackaton2025.screens.TasksScreen
import com.example.hackaton2025.screens.WishlistScreen

@Composable
fun AppNavHost(navController: NavHostController, modifier : Modifier) {
    NavHost(
        navController = navController,
        startDestination = Destinations.OverviewDestination,
        modifier = modifier
    ) {
        composable<Destinations.OverviewDestination> {
            OverviewScreen(navController)
        }
        composable<Destinations.WishlistDestination> {
            WishlistScreen(navController)
        }
        composable<Destinations.TasksDestination> {
            TasksScreen(navController)
        }
        composable<Destinations.GamesDestination> {
            GamesScreen(navController)
        }
    }
}