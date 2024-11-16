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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import tn.esprit.gainupdam.R
import tn.esprit.gainupdam.ViewModel.AuthViewModel

@Composable
fun SignInScreen(navController: NavController, authViewModel: AuthViewModel) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var rememberMe by remember { mutableStateOf(false) }
    var passwordVisible by remember { mutableStateOf(false) }
    val loginState = authViewModel.loginState.value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Image(
            painter = painterResource(id = R.drawable.sign),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(310.dp),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 5.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            EmailInput(email = email, onEmailChange = { email = it })
            PasswordInput(
                password = password,
                onPasswordChange = { password = it },
                passwordVisible = passwordVisible,
                onPasswordVisibleChange = { passwordVisible = it }
            )
            RememberMeCheckbox(rememberMe = rememberMe, onRememberMeChange = { rememberMe = it })
            SignInButton(
                onClick = {
                    authViewModel.login(email, password, rememberMe) { success ->
                        if (success) {
                            navController.navigate("home")
                        }
                    }
                }
            )
            if (loginState.error.isNotEmpty()) {
                Text(
                    text = loginState.error,
                    color = Color.Red,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
            ForgotPasswordLink(navController = navController)
            DividerWithOr()
            SocialSignInButtons(navController = navController)
            SignUpLink(onClick = { navController.navigate("sign_up") })
        }
    }
}

@Composable
fun EmailInput(
    email: String,
    onEmailChange: (String) -> Unit
) {
    var isError by remember { mutableStateOf(false) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(55.dp)
            .padding(bottom = 12.dp)
            .background(Color.White, shape = RoundedCornerShape(8.dp))
            .border(
                width = 2.dp,
                color = if (isError) Color.Red else Color(0xFF0A3D62),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(horizontal = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_email),
            contentDescription = null,
            colorFilter = ColorFilter.tint(Color.Gray),
            modifier = Modifier
                .size(24.dp)
                .padding(end = 8.dp)
        )
        BasicTextField(
            value = email,
            onValueChange = {
                onEmailChange(it)
                isError = it.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(it).matches()
            },
            modifier = Modifier
                .weight(1f)
                .padding(0.dp),
            textStyle = LocalTextStyle.current.copy(color = Color.Black, fontSize = 16.sp),
            decorationBox = { innerTextField ->
                Box(contentAlignment = Alignment.CenterStart) {
                    if (email.isEmpty()) {
                        Text(
                            text = "Email",
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
fun PasswordInput(
    password: String,
    onPasswordChange: (String) -> Unit,
    passwordVisible: Boolean,
    onPasswordVisibleChange: (Boolean) -> Unit
) {
    var isError by remember { mutableStateOf(false) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(55.dp)
            .padding(bottom = 11.dp)
            .background(Color.White, shape = RoundedCornerShape(8.dp))
            .border(
                width = 2.dp,
                color = if (isError) Color.Red else Color(0xFF0A3D62),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(horizontal = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_lock),
            contentDescription = null,
            colorFilter = ColorFilter.tint(Color.Gray),
            modifier = Modifier
                .size(24.dp)
                .padding(end = 8.dp)
        )
        BasicTextField(
            value = password,
            onValueChange = {
                onPasswordChange(it)
                isError = it.isEmpty()
            },
            modifier = Modifier
                .weight(1f)
                .padding(0.dp),
            textStyle = LocalTextStyle.current.copy(color = Color.Black, fontSize = 16.sp),
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            decorationBox = { innerTextField ->
                Box(contentAlignment = Alignment.CenterStart) {
                    if (password.isEmpty()) {
                        Text(
                            text = "Password",
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
fun RememberMeCheckbox(rememberMe: Boolean, onRememberMeChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 9.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = rememberMe,
            onCheckedChange = onRememberMeChange,
            modifier = Modifier.padding(end = 3.dp)
        )
        Text(
            text = "Remember Me",
            color = Color.Gray,
            fontSize = 14.sp
        )
    }
}

@Composable
fun SignInButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(65.dp)
            .padding(bottom = 12.dp),
        shape = RoundedCornerShape(11.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0A3D62))
    ) {
        Text(
            text = "Sign In",
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
@Composable
fun ForgotPasswordLink(navController: NavController) {
    Text(
        text = "Forgot Password?",
        color = Color(0xFF3498DB),
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .clickable { navController.navigate("forgot_password") }
            .padding(bottom = 14.dp)
    )
}

@Composable
fun DividerWithOr() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 15.dp),
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
fun SocialSignInButtons(navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedButton(
            onClick = { /* Handle Google sign-in */ },
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
            onClick = { /* Handle Facebook sign-in */ },
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
fun SignUpLink(onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Don't have an account?",
            color = Color.Gray,
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal
        )
        Text(
            text = " Sign Up",
            color = Color(0xFF3498DB),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.clickable { onClick() }
        )
    }
}
