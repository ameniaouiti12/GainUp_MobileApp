package tn.esprit.gainupdam.ScreensUserMangement

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import tn.esprit.gainupdam.ViewModel.AuthViewModelVerifyOtp

@Composable
fun VerifyOtpScreen(navController: NavController, authViewModelVerifyOtp: AuthViewModelVerifyOtp, email: String) {
    var otp by remember { mutableStateOf("") }
    val verifyOtpState = authViewModelVerifyOtp.verifyOtpState.value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Title "Verify OTP"
        Text(
            text = "Verify OTP",
            style = MaterialTheme.typography.titleLarge,
            color = Color.Black,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Description text
        Text(
            text = "Enter the OTP sent to your email address.",
            color = Color.Gray,
            fontSize = 16.sp,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        // OTP Input
        OtpInputField(
            value = otp,
            onValueChange = { otp = it },
            label = "OTP"
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Verify OTP Button
        Button(onClick = {
            if (otp.isNotEmpty()) {
                authViewModelVerifyOtp.verifyOtp(otp) { success ->
                    if (success) { // No need to cast to Boolean
                        // Navigate to the next screen (e.g., change password screen)
                        navController.navigate("change_password")
                    } else {
                        // Show error message
                        Log.d("VerifyOtpScreen", "Failed to verify OTP")
                    }
                }
            } else {
                // Handle case where OTP is empty (optional)
                Log.d("VerifyOtpScreen", "OTP cannot be empty")
            }
        }) {
            Text("Verify OTP")
        }
    }

        Spacer(modifier = Modifier.height(16.dp))

        // Show error or success message
        if (verifyOtpState.error.isNotEmpty()) {
            Text(
                text = verifyOtpState.error,
                color = Color.Red,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        if (verifyOtpState.message.isNotEmpty()) {
            Text(
                text = verifyOtpState.message,
                color = Color.Green,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }



@Composable
fun OtpInputField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(60.dp)  // Taille augmentée
            .background(Color.White, shape = RoundedCornerShape(8.dp))
            .border(
                width = 2.dp,
                color = Color(0xFF0A3D62),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(horizontal = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .weight(1f)
                .padding(0.dp),
            textStyle = LocalTextStyle.current.copy(color = Color.Black, fontSize = 16.sp),
            decorationBox = { innerTextField ->
                Box(contentAlignment = Alignment.CenterStart) {
                    if (value.isEmpty()) {
                        Text(
                            text = label,
                            color = Color.Gray,
                            fontSize = 16.sp
                        )
                    }
                    innerTextField()
                }
            }
        )
    }
}

@Composable
fun VerifyOtpButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)  // Hauteur du bouton
            .padding(vertical = 10.dp),
        shape = RoundedCornerShape(11.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0A3D62))
    ) {
        Text(
            text = "Verify OTP",
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
