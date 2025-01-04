package tn.esprit.gainupdam.ScreenHome

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import tn.esprit.gainupdam.BottomNavigationBar.BottomNavigation
import tn.esprit.gainupdam.Navigation.TopBar
import tn.esprit.gainupdam.Navigation.WorkoutList

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WorkoutScreen(navController: NavHostController, day: String, onDaySelected: (String) -> Unit) {
    Log.d("WorkoutScreen", "Day parameter: $day")

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
            TopBar(navController, onDaySelected)
            Spacer(modifier = Modifier.height(16.dp))

            WorkoutList(navController, day)
            Spacer(modifier = Modifier.weight(1f))
            BottomNavigation(navController)
        }
    }
}
