package tn.esprit.gainupdam.ScreensUserMangement

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import tn.esprit.gainupdam.R
import tn.esprit.gainupdam.ViewModel.AuthViewModelSinUp

@Composable
fun SignUpScreen(navController: NavController, authViewModelSignUp: AuthViewModelSinUp) {
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var termsAccepted by remember { mutableStateOf(false) }

    // États pour les erreurs
    var fullNameError by remember { mutableStateOf(false) }
    var emailError by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }
    var confirmPasswordError by remember { mutableStateOf(false) }
    var termsError by remember { mutableStateOf(false) }

    // État pour le message de retour
    var signUpMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Image(
            painter = painterResource(id = R.drawable.signup),
            contentDescription = "Sign Up Image",
            modifier = Modifier
                .fillMaxWidth()
                .height(130.dp)
                .padding(bottom = 16.dp)
        )

        Text(
            text = "Sign Up",
            style = MaterialTheme.typography.titleLarge,
            color = Color.Black,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Champs de saisie avec validation
        SignUpInputField(
            value = fullName,
            onValueChange = { fullName = it; fullNameError = it.isEmpty() },
            label = "Full Name",
            isError = fullNameError
        )

        SignUpInputField(
            value = email,
            onValueChange = { email = it; emailError = !android.util.Patterns.EMAIL_ADDRESS.matcher(it).matches() },
            label = "Email",
            isError = emailError
        )

        SignUpPasswordInputField(
            value = password,
            onValueChange = { password = it; passwordError = it.length < 6 },
            label = "Password",
            isError = passwordError
        )

        SignUpPasswordInputField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it; confirmPasswordError = it != password },
            label = "Confirm Password",
            isError = confirmPasswordError
        )

        // Checkbox pour accepter les termes
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 8.dp)
        ) {
            Checkbox(
                checked = termsAccepted,
                onCheckedChange = { termsAccepted = it; termsError = !it }
            )
            Text(text = "I agree to Terms of Service and Privacy Policy", fontSize = 12.sp)
        }

        if (termsError) {
            Text(text = "You must accept the terms", color = Color.Red, fontSize = 12.sp)
        }

        // Bouton Sign Up
        SignUpButton(onClick = {
            // Validation des champs
            fullNameError = fullName.isEmpty()
            emailError = !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
            passwordError = password.length < 6
            confirmPasswordError = confirmPassword != password
            termsError = !termsAccepted

            // Si aucun champ n'est en erreur, appeler performSignUp
            if (!fullNameError && !emailError && !passwordError && !confirmPasswordError && !termsError) {
                authViewModelSignUp.performSignUp(fullName, email, password, confirmPassword) { success, message ->
                    signUpMessage = message
                    if (success) {
                        // Navigate to the next screen or show a success message
                        navController.navigate("sign_in")
                    }
                }
            }
        })

        if (signUpMessage.isNotEmpty()) {
            Text(text = signUpMessage, color = if (signUpMessage.contains("successful")) Color.Green else Color.Red)
        }

        DividerWithOrTwo()

        SocialSignUpButtons(navController)

        SignInLink(onClick = { navController.navigate("sign_in") })
    }
}

@Composable
fun SignUpInputField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    isError: Boolean
) {
    val borderColor = if (isError) Color.Red else Color(0xFF0A3D62)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .padding(bottom = 12.dp)
            .background(Color.White, shape = RoundedCornerShape(8.dp))
            .border(2.dp, borderColor, RoundedCornerShape(8.dp))
            .padding(horizontal = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.weight(1f),
            textStyle = LocalTextStyle.current.copy(color = Color.Black, fontSize = 16.sp),
            decorationBox = { innerTextField ->
                Box(contentAlignment = Alignment.CenterStart) {
                    if (value.isEmpty()) {
                        Text(text = label, color = Color.Gray, fontSize = 16.sp)
                    }
                    innerTextField()
                }
            }
        )
    }

    if (isError) {
        Text(text = "$label is invalid", color = Color.Red, fontSize = 12.sp)
    }
}

@Composable
fun SignUpPasswordInputField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    isError: Boolean
) {
    val borderColor = if (isError) Color.Red else Color(0xFF0A3D62)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .padding(bottom = 12.dp)
            .background(Color.White, shape = RoundedCornerShape(8.dp))
            .border(2.dp, borderColor, RoundedCornerShape(8.dp))
            .padding(horizontal = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.weight(1f),
            textStyle = LocalTextStyle.current.copy(color = Color.Black, fontSize = 16.sp),
            visualTransformation = PasswordVisualTransformation(),
            decorationBox = { innerTextField ->
                Box(contentAlignment = Alignment.CenterStart) {
                    if (value.isEmpty()) {
                        Text(text = label, color = Color.Gray, fontSize = 16.sp)
                    }
                    innerTextField()
                }
            }
        )
    }

    if (isError) {
        Text(text = "$label is invalid", color = Color.Red, fontSize = 12.sp)
    }
}

@Composable
fun SignUpButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .padding(vertical = 10.dp),
        shape = RoundedCornerShape(11.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0A3D62))
    ) {
        Text(
            text = "Sign Up",
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun DividerWithOrTwo() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 18.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Divider(
            color = Color(0xFFD3D3D3),
            modifier = Modifier
                .weight(1f)
                .height(1.dp)
        )
        Text(
            text = "Or",
            color = Color(0xFFB0B0B0),
            fontSize = 14.sp,
            modifier = Modifier.padding(horizontal = 8.dp)
        )
        Divider(
            color = Color(0xFFD3D3D3),
            modifier = Modifier
                .weight(1f)
                .height(1.dp)
        )
    }
}

@Composable
fun SocialSignUpButtons(navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        OutlinedButton(
            onClick = { /* Google Sign-In Logic */ },
            modifier = Modifier
                .width(170.dp)
                .height(45.dp)
                .padding(end = 8.dp),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Black),
            border = BorderStroke(1.dp, Color.Black)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_google),
                contentDescription = null,
                tint = Color.Black,
                modifier = Modifier.padding(end = 8.dp)
            )
            Text(
                text = "Google",
                color = Color.Black,
                fontSize = 16.sp
            )
        }

        Button(
            onClick = { /* Facebook Sign-In Logic */ },
            modifier = Modifier
                .width(170.dp)
                .height(45.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1877F2))
        ) {
            Icon(
                painter = painterResource(id = R.drawable.icon_face),
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.padding(end = 8.dp)
            )
            Text(
                text = "Facebook",
                color = Color.White,
                fontSize = 16.sp
            )
        }
    }
}

@Composable
fun SignInLink(onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 5.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Already have an account? ",
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