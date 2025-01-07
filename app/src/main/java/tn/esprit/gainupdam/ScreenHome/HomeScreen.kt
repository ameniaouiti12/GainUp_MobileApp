package tn.esprit.gainupdam.ScreenHome

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import tn.esprit.gainupdam.BottomNavigationBar.BottomNavigation
import tn.esprit.gainupdam.Navigation.TopBar
import tn.esprit.gainupdam.ViewModel.AuthenticationManager
import tn.esprit.gainupdam.R

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(navController: NavHostController, authManager: AuthenticationManager) {
    var selectedDay by remember { mutableStateOf("Tuesday") }
    var isDarkMode by remember { mutableStateOf(false) }
    var sleepValue by remember { mutableStateOf(0) }
    var waterValue by remember { mutableStateOf(0) }

    // Change background color based on theme mode
    val backgroundColor = if (isDarkMode) Color(0xFF03224c) else Color(0xFFf0f0f0)


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF03224c))
            .padding(13.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()) // Ajouter le défilement vertical
        ) {
            // TopBar
            TopBar(navController, onDaySelected = { day ->
                selectedDay = day
            })


            // Progress Card
            ProgressCard()

            // Calorie Card
            val calorie = 2474
            CalorieCard(calories = calorie)

            // Stats Cards
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                StatCard(
                    title = "Sleep",
                    value = "$sleepValue/8",
                    unit = "Hours",
                    progress = sleepValue / 8.0f,
                    onIncrement = { sleepValue = (sleepValue + 1).coerceAtMost(8) }
                )
                StatCard(
                    title = "Water",
                    value = "$waterValue/5",
                    unit = "Liters",
                    progress = waterValue / 5.0f,
                    onIncrement = { waterValue = (waterValue + 1).coerceAtMost(5) }
                )
            }
        }

        // Bottom Navigation Bar (this will stay at the bottom)
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
        ) {
            BottomNavigation(navController)
        }
    }
}

@Composable
fun ProgressCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF0a1728)),
        shape = RoundedCornerShape(13.dp) // Coins arrondis
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Text(
                text = "Workout Progress!",
                style = MaterialTheme.typography.titleLarge,
                color = Color.White
            )
            Text(
                text = "0 Exercise",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
            MacroProgressBar(label = "", value = "", progress = 0.0f)
        }
    }
}

@Composable
fun StatCard(
    title: String,
    value: String,
    unit: String,
    progress: Float,
    onIncrement: () -> Unit
) {
    Box(
        modifier = Modifier
            .width(140.dp)
            .height(160.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxSize(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF0a1728)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Title
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White
                )

                // Value and Unit
                Column {
                    Text(
                        text = value,
                        style = MaterialTheme.typography.headlineMedium,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = unit,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White
                    )
                }

                // Progress Indicator
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    CircularProgressIndicator(
                        progress = progress,
                        modifier = Modifier.size(50.dp),
                        color = Color(0xFF2196F3),
                        trackColor = Color.White
                    )
                }
            }
        }
        // Increment Icon Button
        IconButton(
            onClick = onIncrement,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(8.dp)
                .size(30.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.add),
                contentDescription = "Increment",
                tint = Color.White,
                modifier = Modifier.size(30.dp)
            )
        }
    }
}

@Composable
fun CalorieCard(calories: Int) {
    val consumedCalories = calories // Example: consumed calories are the same as calculated calories
    val remainingCalories = 2875 - consumedCalories // Example: remaining calories calculation

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF0a1728)),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "0 kcal",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Consumed",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "2474 kcal",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Remaining",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Macronutrients progress bars
            MacroProgressBar(label = "P", value = "0/12g", progress = 0.0f)
            Spacer(modifier = Modifier.height(4.dp))
            MacroProgressBar(label = "C", value = "0/12g", progress = 0.0f)
            Spacer(modifier = Modifier.height(4.dp))
            MacroProgressBar(label = "F", value = "0/12g", progress = 0.0f)
        }
    }
}

@Composable
fun MacroProgressBar(label: String, value: String, progress: Float) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = label, fontSize = 14.sp, color = Color.White)
            Text(text = value, fontSize = 14.sp, color = Color.White)
        }
        LinearProgressIndicator(
            progress = progress,
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp)),
            color = Color(0xFF2196F3)
        )
    }
}
