package tn.esprit.gainupdam.ScreenProfile

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import tn.esprit.gainupdam.BottomNavigationBar.BottomNavigation
import tn.esprit.gainupdam.R


@Composable
fun ProfileScreen(navController: NavHostController) {
    val context = LocalContext.current
    val bitmap = remember { mutableStateOf<Bitmap?>(null) }
    val imageUri = remember { mutableStateOf<Uri?>(null) }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            imageUri.value = uri
            bitmap.value = if (Build.VERSION.SDK_INT < 28) {
                MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
            } else {
                val source = ImageDecoder.createSource(context.contentResolver, uri)
                ImageDecoder.decodeBitmap(source)
            }
        }
    }

    // Define the mutable states for user data
    val name = remember { mutableStateOf("John Doe") }
    val address = remember { mutableStateOf("123 Main St, City, Country") }
    val job = remember { mutableStateOf("Software Engineer") }

    // State for showing logout confirmation dialog
    var showLogoutDialog by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF3A4B6B))
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Toolbar with Quiz Circle and Logout Icon
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Profile Progress Circle (Quiz Circle)
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color.Gray)
                        .border(width = 2.dp, color = Color.White, shape = CircleShape)
                        .clickable {
                            // Navigate to quiz or other action
                        }
                ) {
                    Text(
                        text = "20%",
                        fontSize = 12.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }

                // Logout Button
                IconButton(
                    onClick = { showLogoutDialog = true },
                    modifier = Modifier.size(40.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_logout_24),
                        contentDescription = "Logout",
                        tint = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp)) // Adjust space before the title

            Text(
                text = "Profile",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            // Profile Image with Camera Icon Overlay
            Box(
                contentAlignment = Alignment.BottomEnd,
                modifier = Modifier.size(150.dp)
            ) {
                if (bitmap.value != null) {
                    Image(
                        bitmap = bitmap.value!!.asImageBitmap(),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(Color.Blue)
                            .size(150.dp)
                            .border(
                                width = 1.dp,
                                color = Color.Gray,
                                shape = CircleShape
                            )
                    )
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.baseline_person_24),
                        contentDescription = null,
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(Color.Gray)
                            .size(150.dp)
                    )
                }

                // Camera icon overlay
                Image(
                    painter = painterResource(id = R.drawable.baseline_photo_camera_24),
                    contentDescription = null,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color.Gray)
                        .border(width = 1.dp, color = Color.White, shape = CircleShape)
                        .padding(5.dp)
                        .clickable {
                            imagePickerLauncher.launch("image/*")
                        }
                        .align(Alignment.BottomEnd)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // User Information
            Text(
                text = name.value,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = address.value,
                fontSize = 16.sp,
                color = Color.Gray,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = job.value,
                fontSize = 16.sp,
                color = Color.Gray,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            // Edit Profile and Add Friends Buttons
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = { /* Edit profile action */ },
                    modifier = Modifier
                        .weight(1f)
                        .height(40.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White, // White background for the button
                        contentColor = Color(0xFF2A3C55) // Marine blue text color
                    )
                ) {
                    Text(text = "Edit Profile", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.width(16.dp))

                Button(
                    onClick = { /* Add friends action */ },
                    modifier = Modifier
                        .weight(1f)
                        .height(40.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White, // White background for the button
                        contentColor = Color(0xFF2A3C55) // Marine blue text color
                    )
                ) {
                    Text(text = "Add Friends", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
            }

            // Spacer to push the bottom navigation bar to the bottom
            Spacer(modifier = Modifier.weight(1f))
        }

        // Bottom Navigation Bar
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
        ) {
            BottomNavigation(navController)
        }
    }

    // Logout Confirmation Dialog
    if (showLogoutDialog) {
        AlertDialog(
            onDismissRequest = { showLogoutDialog = false },
            title = { Text("Log out?") },
            text = { Text("You may check out any time you like, but you can never leave.") },
            confirmButton = {
                TextButton(
                    onClick = {
                        // Clear session or authentication state here
                        clearSession(context) // This is the function that clears session data

                        // Navigate to the SignIn screen
                        navController.navigate("sign_in") {
                            popUpTo("profileScreen") { inclusive = true } // Pop the Profile screen from the stack
                        }
                        showLogoutDialog = false
                    }
                ) {
                    Text("Go back")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showLogoutDialog = false }
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}

// Function to clear the session (you can adapt this to your session management)
private fun clearSession(context: Context) {
    // Example: Clear any shared preferences or authentication state
    val sharedPreferences = context.getSharedPreferences("authPrefs", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.clear() // Clear all preferences or specific ones related to authentication
    editor.apply()

    // If you're using a token, you can clear it as well
    // Example: YourTokenStorage.clearToken()
}
