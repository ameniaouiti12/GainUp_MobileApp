
    package tn.esprit.gainupdam.ScreensUserMangement

    import android.util.Log
    import androidx.compose.foundation.background
            import androidx.compose.foundation.border
            import androidx.compose.foundation.clickable
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
            import tn.esprit.gainupdam.ViewModel.AuthViewModel
    import tn.esprit.gainupdam.ViewModel.AuthViewModelForgotPassword

    @Composable
            fun ForgotPasswordScreen(navController: NavController, authViewModelForgotPassword: AuthViewModelForgotPassword) {
                var email by remember { mutableStateOf("") }
                val forgotPasswordState = authViewModelForgotPassword.forgotPasswordState.value

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .background(Color.White),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    // Title "Forgot Password"
                    Text(
                        text = "Forgot Password",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.Black,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold  // Texte en gras
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Description text
                    Text(
                        text = "Enter your email address below to receive a password reset link.",
                        color = Color.Gray,
                        fontSize = 16.sp,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // Email Input
                    ForgotPasswordInputField(
                        value = email,
                        onValueChange = { email = it },
                        label = "Email"
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Reset Password Button
                    ResetPasswordButton(onClick = {
                        if (isValidEmail(email)) {
                            authViewModelForgotPassword.forgotPassword(email) { success ->
                                if (success) {
                                    // Navigation vers la page "Verify OTP" si l'OTP est envoyé avec succès
                                    navController.navigate("verify_otp")
                                } else {
                                    Log.d("ForgotPasswordScreen", "Failed to send OTP")
                                }
                            }
                        } else {
                            Log.d("ForgotPasswordScreen", "Invalid email format")
                        }
                    })

                    Spacer(modifier = Modifier.height(16.dp))

                    // Link to Sign In Screen
                    SignInLinkthree(onClick = { navController.navigate("sign_in") })

                    // Show error or success message
                    if (forgotPasswordState.error.isNotEmpty()) {
                        Text(
                            text = forgotPasswordState.error,
                            color = Color.Red,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }

                    if (forgotPasswordState.message.isNotEmpty()) {
                        Text(
                            text = forgotPasswordState.message,
                            color = Color.Green,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                }
            }

    @Composable
    fun ForgotPasswordInputField(
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
    fun ResetPasswordButton(onClick: () -> Unit) {
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
                text = "Reset Password",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }

    @Composable
    fun SignInLinkthree(onClick: () -> Unit) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Remember your password? ",
                color = Color.Gray,
                fontSize = 16.sp
            )
            Text(
                text = "Sign In",
                color = Color(0xFF3498DB),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable { onClick() }
            )
        }
    }

    // Fonction pour vérifier si l'email est valide
    fun isValidEmail(email: String): Boolean {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        return email.matches(emailPattern.toRegex())
    }
