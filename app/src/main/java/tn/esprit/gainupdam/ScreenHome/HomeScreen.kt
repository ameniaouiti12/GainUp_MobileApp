package tn.esprit.gainupdam.ScreenHome

import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import tn.esprit.gainupdam.BottomNavigationBar.BottomNavigation
import tn.esprit.gainupdam.Navigation.TopBar

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(navController: NavHostController) {
    var selectedDay by remember { mutableStateOf("Tuesday") }

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
            CalorieCard()

            // Stats Cards
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                StatCard(
                    title = "Sleep",
                    value = "5/8",
                    unit = "Hours",
                    progress = 0.625f
                )
                StatCard(
                    title = "Water",
                    value = "3/5",
                    unit = "Liters",
                    progress = 0.6f
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
                text = "14 Exercises left",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
            LinearProgressIndicator(
                progress = 0.75f,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
                    .height(6.dp)
                    .clip(RoundedCornerShape(4.dp)),
                color = Color(0xFF2196F3),
                trackColor = Color(0xFF0a1728)
            )
        }
    }
}

@Composable
fun StatCard(title: String, value: String, unit: String, progress: Float) {
    Card(
        modifier = Modifier
            .width(140.dp)
            .height(140.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF0a1728)),
        shape = RoundedCornerShape(12.dp) // Coins arrondis
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = Color.White
            )
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
                    color = Color.Gray
                )
            }
            CircularProgressIndicator(
                progress = progress,
                modifier = Modifier.size(40.dp),
                color = Color(0xFF2196F3),
                trackColor = Color(0xFF0a1728)
            )
        }
    }
}

@Composable
fun CalorieCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF0a1728)),
        shape = RoundedCornerShape(12.dp) // Coins arrondis
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
                        text = "1456 kcal",
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
                        text = "2875 kcal",
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
            MacroProgressBar(label = "P", value = "10/12g", progress = 0.83f)
            Spacer(modifier = Modifier.height(4.dp))
            MacroProgressBar(label = "C", value = "10/12g", progress = 0.83f)
            Spacer(modifier = Modifier.height(4.dp))
            MacroProgressBar(label = "F", value = "10/12g", progress = 0.83f)
        }
    }
}

@Composable
fun MacroProgressBar(label: String, value: String, progress: Float) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.White,
            modifier = Modifier.width(20.dp)
        )
        LinearProgressIndicator(
            progress = progress,
            modifier = Modifier
                .weight(1f)
                .height(4.dp)
                .clip(RoundedCornerShape(2.dp)),
            color = Color(0xFF2196F3),
            trackColor = Color(0xFF1A1B2E)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray,
            modifier = Modifier.padding(start = 4.dp)
        )
    }
}
