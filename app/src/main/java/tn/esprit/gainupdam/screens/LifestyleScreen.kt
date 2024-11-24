package tn.esprit.gainupdam.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import tn.esprit.gainupdam.ui.theme.DarkBlue
import tn.esprit.gainupdam.ui.theme.Turquoise
import tn.esprit.gainupdam.utils.PreferencesHelper

@Composable
fun LifestyleScreen(navController: NavController) {
    var selectedLifestyle by remember { mutableStateOf("") }
    val context = LocalContext.current

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
            Text("Choose your lifestyle", fontSize = 36.sp, color = Color.White)
            Spacer(modifier = Modifier.height(100.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = selectedLifestyle == "Très passif",
                    onClick = { selectedLifestyle = "Très passif" },
                    colors = RadioButtonDefaults.colors(selectedColor = Turquoise)
                )
                Text("Très passif", fontSize = 34.sp, color = Color.White)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = selectedLifestyle == "Passif",
                    onClick = { selectedLifestyle = "Passif" },
                    colors = RadioButtonDefaults.colors(selectedColor = Turquoise)
                )
                Text("Passif", fontSize = 34.sp, color = Color.White)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = selectedLifestyle == "Normal",
                    onClick = { selectedLifestyle = "Normal" },
                    colors = RadioButtonDefaults.colors(selectedColor = Turquoise)
                )
                Text("Normal", fontSize = 34.sp, color = Color.White)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = selectedLifestyle == "Actif",
                    onClick = { selectedLifestyle = "Actif" },
                    colors = RadioButtonDefaults.colors(selectedColor = Turquoise)
                )
                Text("Actif", fontSize = 34.sp, color = Color.White)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = selectedLifestyle == "Très actif",
                    onClick = { selectedLifestyle = "Très actif" },
                    colors = RadioButtonDefaults.colors(selectedColor = Turquoise)
                )
                Text("Très actif", fontSize = 34.sp, color = Color.White)
            }
            Spacer(modifier = Modifier.height(32.dp))
            Button(
                onClick = {
                    if (selectedLifestyle.isNotEmpty()) {
                        PreferencesHelper.setQuizCompleted(context, true)
                        navController.navigate("home")
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Turquoise)
            ) {
                Text("Finish", fontSize = 34.sp)
            }
        }
    }
}
