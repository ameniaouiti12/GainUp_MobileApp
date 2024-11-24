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
fun GoalScreen(navController: NavController) {
    var selectedGoal by remember { mutableStateOf("") }

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
            Text("Choose your goal", fontSize = 40.sp, color = Color.White)
            Spacer(modifier = Modifier.height(100.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = selectedGoal == "Bulking",
                    onClick = { selectedGoal = "Bulking" },
                    colors = RadioButtonDefaults.colors(selectedColor = Turquoise)
                )
                Text("Bulking", fontSize = 34.sp, color = Color.White)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = selectedGoal == "Cutting",
                    onClick = { selectedGoal = "Cutting" },
                    colors = RadioButtonDefaults.colors(selectedColor = Turquoise)
                )
                Text("Cutting", fontSize = 34.sp, color = Color.White)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = selectedGoal == "Body Recomposition",
                    onClick = { selectedGoal = "Body Recomposition" },
                    colors = RadioButtonDefaults.colors(selectedColor = Turquoise)
                )
                Text("Body Recomposition", fontSize = 34.sp, color = Color.White)
            }
            Spacer(modifier = Modifier.height(32.dp))
            Button(
                onClick = {
                    if (selectedGoal.isNotEmpty()) {
                        navController.navigate("lifestyle")
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Turquoise)
            ) {
                Text("Next", fontSize = 34.sp)
            }
        }
    }
}
