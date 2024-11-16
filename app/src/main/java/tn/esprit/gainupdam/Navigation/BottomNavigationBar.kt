package tn.esprit.gainupdam.BottomNavigationBar

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
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

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(90.dp) // Augmenter la hauteur de la carte
            .padding(3.dp), // Ajuster le padding pour plus d'espace
        shape = RoundedCornerShape(14.dp), // Augmenter le rayon des coins arrondis
        elevation = CardDefaults.cardElevation(12.dp), // Augmenter l'élévation pour plus de profondeur
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
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
                            modifier = Modifier.size(35.dp) // Réduire la taille de l'icône à 30.dp
                        )
                    },
                    label = {
                        Text(
                            text = item.title,
                            color = if (currentRoute == item.route) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
                            fontSize = 9.sp // Diminuer la taille du texte à 9.sp
                        )
                    },
                    selected = currentRoute == item.route,
                    onClick = {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId)
                            launchSingleTop = true
                        }
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        unselectedIconColor = MaterialTheme.colorScheme.onSurface,
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        unselectedTextColor = MaterialTheme.colorScheme.onSurface,
                        indicatorColor = MaterialTheme.colorScheme.primaryContainer
                    )
                )
            }
        }
    }
}
