package com.example.hackaton2025.navigation

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.hackaton2025.R

@Composable
fun  AppBottomNavigationBar(
    navController: NavController
) {
    val selectedNavigationIndex = rememberSaveable {
        mutableIntStateOf(0)
    }

    val navigationItems = listOf(
        NavigationItem(
            title = "Overview",
            iconVector = Icons.Default.Home,
            destination = Destinations.OverviewDestination
        ),
        NavigationItem(
            title = "Wishlist",
            iconVector = Icons.Default.Favorite,
            destination = Destinations.WishlistDestination
        ),
        NavigationItem(
            title = "Tasks",
            iconVector = Icons.Default.Done,
            destination = Destinations.TasksDestination
        ),
        NavigationItem(
            title = "Games",
            iconPainter = painterResource(R.drawable.game_controller),
            destination = Destinations.GamesDestination
        )
    )

    NavigationBar(
        containerColor = colorResource(R.color.blue100)
    ) {
        navigationItems.forEachIndexed { index, item -> 
            NavigationBarItem(
                selected = selectedNavigationIndex.intValue == index,
                onClick = {
                    selectedNavigationIndex.intValue = index
                    navController.navigate(item.destination)
                },
                icon = {
                    val modifier = Modifier.size(32.dp)
                    if (item.iconVector != null) {
                        Icon(
                            imageVector = item.iconVector,
                            contentDescription = item.title,
                            modifier
                        )
                    } else if (item.iconPainter != null) {
                        Icon(
                            painter = item.iconPainter,
                            contentDescription = item.title,
                            modifier
                        )
                    }
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
    val iconVector: ImageVector? = null,
    val iconPainter: Painter? = null,
    val destination: Any
)