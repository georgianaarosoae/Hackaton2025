package com.example.hackaton2025.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.navigation.NavController
import com.example.hackaton2025.R

@Composable
fun AppBottomNavigationBar(
    navController: NavController
) {
    val selectedNavigationIndex = rememberSaveable {
        mutableIntStateOf(0)
    }

    val navigationItems = listOf(
        NavigationItem(
            title = "Overview",
            icon = Icons.Default.Home,
            destination = Destinations.OverviewDestination
        ),
        NavigationItem(
            title = "Wishlist",
            icon = Icons.Default.Person,
            destination = Destinations.WishlistDestination
        ),
        NavigationItem(
            title = "Tasks",
            icon = Icons.Default.ShoppingCart,
            destination = Destinations.TasksDestination
        ),
        NavigationItem(
            title = "Games",
            icon = Icons.Default.Settings,
            destination = Destinations.GamesDestination
        )
    )

    NavigationBar(
        containerColor = Color.White
    ) {
        navigationItems.forEachIndexed { index, item -> 
            NavigationBarItem(
                selected = selectedNavigationIndex.intValue == index,
                onClick = {
                    selectedNavigationIndex.intValue = index
                    navController.navigate(item.destination)
                },
                icon = {
                    Icon(imageVector = item.icon, contentDescription = item.title)
                },
                label = {
                    Text(item.title)
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = colorResource(R.color.waterBlue800),
                    selectedIconColor = colorResource(R.color.odysseyBlue100),
                    unselectedIconColor = colorResource(R.color.waterBlue400),
                    selectedTextColor = colorResource(R.color.waterBlue800),
                    unselectedTextColor = colorResource(R.color.waterBlue400),
                )
            )
        }
    }
}

data class NavigationItem(
    val title: String,
    val icon: ImageVector,
    val destination: Any
)