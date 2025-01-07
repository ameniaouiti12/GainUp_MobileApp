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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import tn.esprit.gainupdam.ui.theme.DarkBlue
import tn.esprit.gainupdam.utils.PreferencesHelper
import tn.esprit.gainupdam.viewmodel.QuizViewModel

@Composable
fun LifestyleScreen(navController: NavController) {
    var selectedLifestyle by remember { mutableStateOf("") }
    val context = LocalContext.current

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
                text = "Choose your lifestyle",
                fontSize = 24.sp,
                color = DarkBlue,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(50.dp))
            Column(
                modifier = Modifier.padding(horizontal = 32.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    RadioButton(
                        selected = selectedLifestyle == "Très passif",
                        onClick = { selectedLifestyle = "Très passif" },
                        colors = RadioButtonDefaults.colors(selectedColor = Color(0xFF2196F3))
                    )
                    Text("Très passif", fontSize = 20.sp, color = DarkBlue, modifier = Modifier.padding(start = 8.dp))
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    RadioButton(
                        selected = selectedLifestyle == "Passif",
                        onClick = { selectedLifestyle = "Passif" },
                        colors = RadioButtonDefaults.colors(selectedColor = Color(0xFF2196F3))
                    )
                    Text("Passif", fontSize = 20.sp, color = DarkBlue, modifier = Modifier.padding(start = 8.dp))
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    RadioButton(
                        selected = selectedLifestyle == "Normal",
                        onClick = { selectedLifestyle = "Normal" },
                        colors = RadioButtonDefaults.colors(selectedColor = Color(0xFF2196F3))
                    )
                    Text("Normal", fontSize = 20.sp, color = DarkBlue, modifier = Modifier.padding(start = 8.dp))
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    RadioButton(
                        selected = selectedLifestyle == "Actif",
                        onClick = { selectedLifestyle = "Actif" },
                        colors = RadioButtonDefaults.colors(selectedColor = Color(0xFF2196F3))
                    )
                    Text("Actif", fontSize = 20.sp, color = DarkBlue, modifier = Modifier.padding(start = 8.dp))
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    RadioButton(
                        selected = selectedLifestyle == "Très actif",
                        onClick = { selectedLifestyle = "Très actif" },
                        colors = RadioButtonDefaults.colors(selectedColor = Color(0xFF2196F3))
                    )
                    Text("Très actif", fontSize = 20.sp, color = DarkBlue, modifier = Modifier.padding(start = 8.dp))
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
            Button(
                onClick = {
                    if (selectedLifestyle.isNotEmpty()) {
                        PreferencesHelper.setQuizCompleted(context, true)
                        navController.navigate("calorie")
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2196F3), contentColor = Color.White),
                modifier = Modifier
                    .padding(horizontal = 32.dp)
                    .height(64.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
            ) {
                Text("Next", fontSize = 20.sp)
            }
        }
    }
}
