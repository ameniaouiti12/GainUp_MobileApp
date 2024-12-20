package tn.esprit.gainupdam.ScreenHome

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import tn.esprit.gainupdam.BottomNavigationBar.BottomNavigation
import tn.esprit.gainupdam.Navigation.TopBar
import tn.esprit.gainupdam.Navigation.WorkoutList

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WorkoutScreen(navController: NavHostController) {
    var isDarkMode by remember { mutableStateOf(false) }

    // Change background color based on theme mode
    val backgroundColor = if (isDarkMode) Color(0xFF03224c) else Color(0xFFf0f0f0)

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = backgroundColor
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            TopBar(navController, onThemeToggle = {

                // Toggle between light and dark mode
                isDarkMode = !isDarkMode
                val mode = if (isDarkMode) {
                    AppCompatDelegate.MODE_NIGHT_YES
                } else {
                    AppCompatDelegate.MODE_NIGHT_NO
                }
                AppCompatDelegate.setDefaultNightMode(mode)
            })
            Spacer(modifier = Modifier.height(16.dp))

            WorkoutList(navController)
            Spacer(modifier = Modifier.weight(1f))
            BottomNavigation(navController)
        }
    }
}
