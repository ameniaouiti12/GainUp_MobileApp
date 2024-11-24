package tn.esprit.gainupdam.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import tn.esprit.gainupdam.ui.theme.DarkBlue
import tn.esprit.gainupdam.ui.theme.Turquoise

@Composable
fun AgeScreen(navController: NavController) {
    var age by remember { mutableStateOf(0f) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBlue)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Choose your age", fontSize = 40.sp, color = Color.White)
            Spacer(modifier = Modifier.height(100.dp))
            Slider(
                value = age,
                onValueChange = { age = it },
                valueRange = 10f..80f,
                steps = 69,
                colors = SliderDefaults.colors(
                    thumbColor = Turquoise,
                    activeTrackColor = Turquoise
                )
            )
            Text("Age: ${age.toInt()}", fontSize = 34.sp, color = Color.White)
            Spacer(modifier = Modifier.height(32.dp))
            Button(
                onClick = { navController.navigate("height") },
                colors = ButtonDefaults.buttonColors(containerColor = Turquoise)
            ) {
                Text("Next", fontSize = 34.sp)
            }
        }
    }
}
