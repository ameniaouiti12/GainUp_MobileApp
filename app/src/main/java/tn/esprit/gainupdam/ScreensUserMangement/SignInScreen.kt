package tn.esprit.gainupdam.ScreensUserMangement

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
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
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.GraphRequest
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import tn.esprit.gainupdam.R
import tn.esprit.gainupdam.ViewModel.AuthViewModel
import tn.esprit.gainupdam.utils.PreferencesHelper
import tn.esprit.gainupdam.utils.handleGoogleSignInSuccess

@Composable
fun SignInScreen(navController: NavController, authViewModel: AuthViewModel, callbackManager: CallbackManager, context: ComponentActivity) {
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
                            if (PreferencesHelper.isQuizCompleted(context)) {
                                navController.navigate("home")
                            } else {
                                navController.navigate("gender")
                            }
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
            SocialSignInButtons(navController = navController, callbackManager = callbackManager, context = context)
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
fun SocialSignInButtons(navController: NavController, callbackManager: CallbackManager, context: ComponentActivity) {
    val googleSignInClient = remember {
        GoogleSignIn.getClient(context, GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("1019999665797-g988f366sdht05en53j05519on5ou5mm.apps.googleusercontent.com")  // Remplacez par votre ID client
            .requestEmail()
            .build())
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedButton(
            onClick = { signInWithGoogle(googleSignInClient, context, navController) },
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
            onClick = { facebookSignIn(navController, callbackManager, context) },
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

fun signInWithGoogle(googleSignInClient: GoogleSignInClient, context: Context, navController: NavController) {
    val signInIntent = googleSignInClient.signInIntent
    (context as Activity).startActivityForResult(signInIntent, 101)
}

fun handleGoogleSignInResult(task: Task<GoogleSignInAccount>, navController: NavController, context: Context) {
    try {
        val account = task.getResult(ApiException::class.java)
        handleGoogleSignInSuccess(account, navController, context)
    } catch (e: ApiException) {
        Toast.makeText(context, "Erreur de connexion Google: ${e.message}", Toast.LENGTH_SHORT).show()
    }
}

fun facebookSignIn(navController: NavController, callbackManager: CallbackManager, context: ComponentActivity) {
    val loginManager = LoginManager.getInstance()
    loginManager.logInWithReadPermissions(context, listOf("email", "public_profile"))
    loginManager.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
        override fun onCancel() {
            Toast.makeText(context.applicationContext, "Connexion annulée", Toast.LENGTH_SHORT).show()
        }

        override fun onError(error: FacebookException) {
            Toast.makeText(context.applicationContext, "Erreur d'authentification Facebook", Toast.LENGTH_SHORT).show()
        }

        override fun onSuccess(result: LoginResult) {
            val accessToken = result.accessToken
            getFacebookUserInfo(accessToken, navController, context)
        }
    })
}

private fun getFacebookUserInfo(accessToken: AccessToken, navController: NavController, context: ComponentActivity) {
    val request = GraphRequest.newMeRequest(accessToken) { `object`, _ ->
        try {
            val name = `object`?.getString("name")
            val email = `object`?.getString("email")
            handleUserInfo(name, email, navController, context)
        } catch (e: Exception) {
            Toast.makeText(context.applicationContext, "Erreur lors de la récupération des informations", Toast.LENGTH_SHORT).show()
        }
    }
    val parameters = Bundle()
    parameters.putString("fields", "id,name,email")
    request.parameters = parameters
    request.executeAsync()
}

private fun handleUserInfo(name: String?, email: String?, navController: NavController, context: ComponentActivity) {
    if (!name.isNullOrEmpty() && !email.isNullOrEmpty()) {
        Toast.makeText(context.applicationContext, "Bienvenue $name", Toast.LENGTH_SHORT).show()
        if (PreferencesHelper.isQuizCompleted(context)) {
            navController.navigate("home")
        } else {
            navController.navigate("gender")
        }
    } else {
        Toast.makeText(context.applicationContext, "Informations Facebook invalides", Toast.LENGTH_SHORT).show()
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
