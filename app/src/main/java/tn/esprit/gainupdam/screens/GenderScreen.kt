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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import tn.esprit.gainupdam.ui.theme.DarkBlue
import tn.esprit.gainupdam.viewmodel.QuizViewModel

@Composable
fun GenderScreen(navController: NavController) {
    var selectedGender by remember { mutableStateOf("") }

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
                text = "Choose your gender",
                fontSize = 24.sp,
                color = DarkBlue,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(50.dp))
            Button(
                onClick = {
                    selectedGender = "Male"
                    navController.navigate("age")
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2196F3), contentColor = Color.White),
                modifier = Modifier
                    .padding(horizontal = 32.dp)
                    .height(56.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
            ) {
                Text("Male", fontSize = 20.sp)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    selectedGender = "Female"
                    navController.navigate("age")
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE91E63), contentColor = Color.White),
                modifier = Modifier
                    .padding(horizontal = 32.dp)
                    .height(56.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
            ) {
                Text("Female", fontSize = 20.sp)
            }
        }
    }
}
