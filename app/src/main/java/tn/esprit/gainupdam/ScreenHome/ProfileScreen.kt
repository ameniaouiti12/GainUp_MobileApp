package tn.esprit.gainupdam.ViewModel

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import tn.esprit.gainupdam.ScreensUserMangement.clearSession

@SuppressLint("RememberReturnType")
@Composable
fun ProfileScreen(navController: NavHostController) {
    val context = LocalContext.current
    val bitmap = remember { mutableStateOf<Bitmap?>(null) }
    val imageUri = remember { mutableStateOf<Uri?>(null) }
    var showLogoutDialog by remember { mutableStateOf(false) } // Track dialog visibility

    // Image picker launcher
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

    val name = remember { mutableStateOf("John Doe") }
    val address = remember { mutableStateOf("123 Main St, City, Country") }
    val job = remember { mutableStateOf("Software Engineer") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF03224c))
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Profile",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            // Photo de profil
            Box(
                contentAlignment = Alignment.BottomEnd,
                modifier = Modifier.size(120.dp)
            ) {
                if (bitmap.value != null) {
                    Image(
                        bitmap = bitmap.value!!.asImageBitmap(),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .clip(CircleShape)
                            .border(2.dp, Color(0xFF2196F3), CircleShape)
                            .size(120.dp)
                    )
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.user),
                        contentDescription = null,
                        modifier = Modifier
                            .clip(CircleShape)
                            .border(2.dp, Color(0xFF2196F3), CircleShape)
                            .size(120.dp)
                    )
                }

                Image(
                    painter = painterResource(id = R.drawable.baseline_photo_camera_24),
                    contentDescription = null,
                    modifier = Modifier
                        .size(35.dp)
                        .clip(CircleShape)
                        .background(Color.Gray)
                        .border(1.dp, Color.White, CircleShape)
                        .clickable { imagePickerLauncher.launch("image/*") }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = name.value,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Buttons for profile actions
            val buttonModifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(vertical = 4.dp)

            Button(
                onClick = {  navController.navigate("editProfileScreen")},
                modifier = buttonModifier,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF0a1728),
                    contentColor = Color.White
                ),
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.user),
                        contentDescription = null,
                        tint = Color(0xFF2196F3),
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(20.dp))
                    Text(
                        text = "Personal Info",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.width(108.dp))
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_arrow_forword_24),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
            // Notification Button
            Button(
                onClick = { /* Notifications Action */ },
                modifier = buttonModifier,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF0a1728), // Navy blue background
                    contentColor = Color.White // White text and icons
                ),
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_notifications), // Add icon resource
                        contentDescription = null,
                        tint = Color(0xFF2196F3), // Blue color for the icon
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(20.dp))
                    Text(
                        text = "Notification",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.width(118.dp))
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_arrow_forword_24),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            // Settings Button
            Button(
                onClick = { /* Settings Action */ },
                modifier = buttonModifier,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF0a1728), // Navy blue background
                    contentColor = Color.White // White text and icons
                ),
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_settings), // Add icon resource
                        contentDescription = null,
                        tint = Color(0xFF2196F3), // Blue color for the icon
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(20.dp))
                    Text(
                        text = "Settings",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.width(140.dp))
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_arrow_forword_24),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            // Help & Support Button
            Button(
                onClick = { /* Help & Support Action */ },
                modifier = buttonModifier,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF0a1728), // Navy blue background
                    contentColor = Color.White // White text and icons
                ),
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_help), // Add icon resource
                        contentDescription = null,
                        tint = Color(0xFF2196F3), // Blue color for the icon
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(20.dp))
                    Text(
                        text = "Help & Support",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.width(96.dp))
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_arrow_forword_24),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(25.dp))


            // Sign Out Button
            Button(
                onClick = { showLogoutDialog = true },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF0a1728),
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .border(
                        2.dp,
                        Color(0xFF2196F3),
                        RoundedCornerShape(16.dp)
                    )
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_logout_24),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Sign Out", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        }

        // Bottom Navigation Bar
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
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
                        clearSession(context)

                        // Navigate to the SignIn screen
                        navController.navigate("sign_in") {
                            popUpTo("profileScreen") { inclusive = true }
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

// Function to clear the session
fun clearSession(context: Context) {
    val sharedPreferences = context.getSharedPreferences("authPrefs", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.clear()
    editor.apply()
}
