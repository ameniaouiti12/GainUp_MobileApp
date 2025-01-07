package tn.esprit.gainupdam.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import tn.esprit.gainupdam.ui.theme.DarkBlue

@Composable
fun CalorieScreen(navController: NavController) {
    var calories by remember { mutableStateOf(0) }

    // Perform the calculation based on the user's inputs
    LaunchedEffect(Unit) {
        val age = 25
        val gender = "female"
        val height = 172
        val weight = 81
        val goal = "Cutting"
        val lifestyle = "normal"

        calories = when (gender) {
            "Male" -> (10 * weight + 6.25 * height - 5 * age + 5) * when (lifestyle) {
                "Très passif" -> 1.2
                "Passif" -> 1.375
                "Normal" -> 1.55
                "Actif" -> 1.725
                "Très actif" -> 1.9
                else -> 1.0
            }
            "Female" -> (10 * weight + 6.25 * height - 5 * age - 161) * when (lifestyle) {
                "Très passif" -> 1.2
                "Passif" -> 1.375
                "Normal" -> 1.55
                "Actif" -> 1.725
                "Très actif" -> 1.9
                else -> 1.0
            }
            else -> 0
        }.toInt()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Your Calorie Needs",
                fontSize = 24.sp,
                color = DarkBlue,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(50.dp))
            Text("Calories: 2474 kcal", fontSize = 20.sp, color = DarkBlue)
            Spacer(modifier = Modifier.height(32.dp))
            Button(
                onClick = { navController.navigate("home") },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2196F3), contentColor = Color.White),
                modifier = Modifier
                    .padding(horizontal = 32.dp)
                    .height(64.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
            ) {
                Text("Finish", fontSize = 20.sp)
            }
        }
    }
}
