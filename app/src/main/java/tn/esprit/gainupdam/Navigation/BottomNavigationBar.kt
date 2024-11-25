package tn.esprit.gainupdam.BottomNavigationBar

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import tn.esprit.gainupdam.R

sealed class BottomNavItem(
    val route: String,
    val icon: @Composable () -> Painter,
    val title: String
) {
    object Home : BottomNavItem("home", { painterResource(id = R.drawable.house) }, "Home")
    object Workout : BottomNavItem("workout", { painterResource(id = R.drawable.muscle) }, "Workout")
    object Messages : BottomNavItem("messages", { painterResource(id = R.drawable.chat) }, "Discussion")
    object Nutrition : BottomNavItem("nutrition", { painterResource(id = R.drawable.plan) }, "Nutrition")
    object Profile : BottomNavItem("profile", { painterResource(id = R.drawable.user) }, "Profile")
}

@Composable
fun BottomNavigation(navController: NavController) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Workout,
        BottomNavItem.Messages,
        BottomNavItem.Nutrition,
        BottomNavItem.Profile
    )

    var selectedItem by remember { mutableStateOf<BottomNavItem>(BottomNavItem.Home) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(75.dp) // Augmenter la hauteur de la carte
            .padding(4.dp), // Ajuster le padding pour plus d'espace
        shape = RoundedCornerShape(14.dp), // Augmenter le rayon des coins arrondis
        elevation = CardDefaults.cardElevation(12.dp), // Augmenter l'élévation pour plus de profondeur
        colors = CardDefaults.cardColors(
            containerColor = Color.White, // Set the card background color to white
            contentColor = MaterialTheme.colorScheme.onSurface
        )
    ) {
        NavigationBar(
            containerColor = MaterialTheme.colorScheme.surface,
            tonalElevation = 0.dp // No elevation inside the card
        ) {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route

            items.forEach { item ->
                NavigationBarItem(
                    icon = {
                        Icon(
                            painter = item.icon(),
                            contentDescription = item.title,
                            tint = if (currentRoute == item.route) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.size(28.dp) // Réduire la taille de l'icône à 30.dp
                        )
                    },
                    label = {
                        Text(
                            text = item.title,
                            color = if (currentRoute == item.route) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
                            fontSize = 7.sp // Diminuer la taille du texte à 9.sp
                        )
                    },
                    selected = currentRoute == item.route,
                    onClick = {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId)
                            launchSingleTop = true
                        }
                        selectedItem = item // Set the selected item when clicked
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFF3161F6), // Custom selected icon color
                        unselectedIconColor = Color(0xFFB0B0B0), // Custom unselected icon color
                        selectedTextColor = Color(0xFF3161F6), // Custom selected text color
                        unselectedTextColor = Color(0xFFB0B0B0), // Custom unselected text color
                    )
                )
            }
        }
    }
}
