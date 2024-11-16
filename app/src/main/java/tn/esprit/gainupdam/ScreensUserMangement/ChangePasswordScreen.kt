package tn.esprit.gainupdam.ScreensUserMangement

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
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import tn.esprit.gainupdam.R

@Composable
fun ChangePasswordScreen(navController: NavController) {
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Title "Change Password"
        Text(
            text = "Change Password",
            style = MaterialTheme.typography.titleLarge,
            color = Color.Black,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold  // Texte en gras
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Description text
        Text(
            text = "Enter your new password and confirm it below.",
            color = Color.Gray,
            fontSize = 16.sp,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        // New Password Input
        PasswordInputField(
            value = newPassword,
            onValueChange = { newPassword = it },
            label = "New Password",
            passwordVisible = passwordVisible,
            onPasswordVisibleChange = { passwordVisible = it }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Confirm Password Input
        PasswordInputField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = "Confirm Password",
            passwordVisible = passwordVisible,
            onPasswordVisibleChange = { passwordVisible = it }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Change Password Button
        ChangePasswordButton(onClick = { /* Change Password Logic */ })

        Spacer(modifier = Modifier.height(16.dp))

        // Link to Sign In Screen
        SignInLink(onClick = { navController.navigate("sign_in") })
    }
}

@Composable
fun PasswordInputField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    passwordVisible: Boolean,
    onPasswordVisibleChange: (Boolean) -> Unit,
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
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
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
        IconButton(onClick = { onPasswordVisibleChange(!passwordVisible) }) {
            Icon(
                painter = if (passwordVisible) painterResource(id = R.drawable.ic_visibility) else painterResource(id = R.drawable.ic_visibility_off),
                contentDescription = null,
                tint = Color.Gray
            )
        }
    }
}

@Composable
fun ChangePasswordButton(onClick: () -> Unit) {
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
            text = "Change Password",
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun SignInLinkR(onClick: () -> Unit) {
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
