package tn.esprit.gainupdam.ScreenHome

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import tn.esprit.gainupdam.BottomNavigationBar.BottomNavigation
import tn.esprit.gainupdam.Navigation.MealsList
import tn.esprit.gainupdam.Navigation.TopBar

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NutritionScreen(navController: NavHostController) {
    var selectedDay by remember { mutableStateOf("Tuesday") }
    val backgroundColor = Color(0xFF03224c)

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = backgroundColor
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            TopBar(navController, onDaySelected = { day ->
                selectedDay = day
            })
            Spacer(modifier = Modifier.height(16.dp))

            MealsList(navController, selectedDay)
            Spacer(modifier = Modifier.weight(1f))
            BottomNavigation(navController)
        }
    }
}
