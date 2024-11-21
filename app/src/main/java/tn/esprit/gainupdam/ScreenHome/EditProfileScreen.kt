@file:OptIn(ExperimentalMaterial3Api::class)

package tn.esprit.gainupdam.ScreenHome

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(navController: NavHostController) {
    var name by remember { mutableStateOf("John Doe") }
    var email by remember { mutableStateOf("john.doe@example.com") }
    var password by remember { mutableStateOf("password123") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF03224c)),
        contentAlignment = Alignment.Center // Center the content in the middle of the screen
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Edit Profile",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            // Name Input
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name", color = Color.White) },
                modifier = Modifier
                    .width(300.dp) // Reduce width of the TextField
                    .background(Color(0xFF0a1728)),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    containerColor = Color(0xFF0a1728),
                    focusedBorderColor = Color(0xFF2196F3),
                    unfocusedBorderColor = Color(0xFF2196F3),
                    focusedLabelColor = Color(0xFF2196F3),
                    unfocusedLabelColor = Color(0xFF2196F3),
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                ),
                keyboardActions = KeyboardActions(onDone = { /* Handle the Done action if needed */ }),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Email Input
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email", color = Color.White) },
                modifier = Modifier
                    .width(300.dp) // Reduce width of the TextField
                    .background(Color(0xFF0a1728)),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    containerColor = Color(0xFF0a1728),
                    focusedBorderColor = Color(0xFF2196F3),
                    unfocusedBorderColor = Color(0xFF2196F3),
                    focusedLabelColor = Color(0xFF2196F3),
                    unfocusedLabelColor = Color(0xFF2196F3),
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                ),
                keyboardActions = KeyboardActions(onDone = { /* Handle the Done action if needed */ }),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Password Input
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password", color = Color.White) },
                modifier = Modifier
                    .width(300.dp) // Reduce width of the TextField
                    .background(Color(0xFF0a1728)),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    containerColor = Color(0xFF0a1728),
                    focusedBorderColor = Color(0xFF2196F3),
                    unfocusedBorderColor = Color(0xFF2196F3),
                    focusedLabelColor = Color(0xFF2196F3),
                    unfocusedLabelColor = Color(0xFF2196F3),
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                ),
                keyboardActions = KeyboardActions(onDone = { /* Handle the Done action if needed */ }),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Save Changes Button
            Button(
                onClick = { /* Handle saving changes */ },
                modifier = Modifier
                    .width(300.dp) // Match width of TextFields
                    .height(50.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .border(2.dp, Color(0xFF2196F3), RoundedCornerShape(16.dp)),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF0a1728),
                    contentColor = Color.White
                )
            ) {
                Text(text = "Save Changes", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Cancel Button
            OutlinedButton(
                onClick = { navController.navigateUp() },
                modifier = Modifier
                    .width(300.dp) // Match width of TextFields
                    .height(50.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .border(2.dp, Color(0xFF2196F3), RoundedCornerShape(16.dp)),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Color.White
                )
            ) {
                Text(text = "Cancel", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}
