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
fun HeightScreen(navController: NavController) {
    var height by remember { mutableStateOf(0f) }

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
            Text("Choose your height", fontSize = 40.sp, color = Color.White)
            Spacer(modifier = Modifier.height(100.dp))
            Slider(
                value = height,
                onValueChange = { height = it },
                valueRange = 100f..210f,
                steps = 109,
                colors = SliderDefaults.colors(
                    thumbColor = Turquoise,
                    activeTrackColor = Turquoise
                )
            )
            Text("Height: ${height.toInt()} cm", fontSize = 34.sp, color = Color.White)
            Spacer(modifier = Modifier.height(32.dp))
            Button(
                onClick = { navController.navigate("weight") },
                colors = ButtonDefaults.buttonColors(containerColor = Turquoise)
            ) {
                Text("Next", fontSize = 34.sp)
            }
        }
    }
}
