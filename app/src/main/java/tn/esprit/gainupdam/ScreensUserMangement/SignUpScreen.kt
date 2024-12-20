package tn.esprit.gainupdam.ScreensUserMangement

import android.app.Activity
import android.content.Context
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.facebook.CallbackManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import tn.esprit.gainupdam.R
import tn.esprit.gainupdam.ViewModel.AuthViewModelSignUp
import tn.esprit.gainupdam.utils.SharedPreferencesUtils

@Composable
fun SignUpScreen(
    navController: NavController,
    authViewModelSignUp: AuthViewModelSignUp,
    callbackManager: CallbackManager,
    context: ComponentActivity
) {
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
                        // Sauvegarder les informations de l'utilisateur dans les SharedPreferences
                        SharedPreferencesUtils.saveUserEmail(context, email)
                        SharedPreferencesUtils.savePassword(context, password)
                        SharedPreferencesUtils.saveUserName(context, fullName)
                        authViewModelSignUp.dismissSignUpDialog()
                    }
                }
            }
        })

        if (signUpMessage.isNotEmpty()) {
            Text(text = signUpMessage, color = if (signUpMessage.contains("successful")) Color.Green else Color.Red)
        }

        DividerWithOrTwo()

        SocialSignUpButtons(navController, callbackManager, context)

        SignInLink(onClick = { navController.navigate("sign_in") })
    }

    if (authViewModelSignUp.isLoading.value) {
        CircularProgressIndicator(
            color = Color(0xFF0A3D62),
            modifier = Modifier
                .padding(vertical = 16.dp)
        )
    }

    if (authViewModelSignUp.showSignUpDialog.value) {
        AlertDialog(
            onDismissRequest = { authViewModelSignUp.dismissSignUpDialog() },
            title = { Text("Sign Up Successful") },
            text = { Text("You have successfully signed up!") },
            confirmButton = {
                TextButton(
                    onClick = {
                        // Clear session or authentication state here
                        clearSession(context) // This is the function that clears session data

                        // Navigate to the SignIn screen
                        navController.navigate("sign_in") {
                            popUpTo("sign_up") { inclusive = true } // Pop the SignUp screen from the stack
                        }
                        authViewModelSignUp.dismissSignUpDialog()
                    }
                ) {
                    Text("OK")
                }
            }
        )
    }
}


fun clearSession(context: Context) {
    // Clear session data or authentication state here
    // For example, you can clear SharedPreferences or any other session data
    val sharedPreferences = context.getSharedPreferences("session_prefs", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.clear()
    editor.apply()
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
fun SocialSignUpButtons(navController: NavController, callbackManager: CallbackManager, context: ComponentActivity) {
    val googleSignInClient = remember {
        GoogleSignIn.getClient(context, GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("1019999665797-g988f366sdht05en53j05519on5mm.apps.googleusercontent.com")  // Remplacez par votre ID client
            .requestEmail()
            .build())
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        OutlinedButton(
            onClick = { signUpWithGoogle(googleSignInClient, context) },
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

        // Facebook Button
        Button(
            onClick = { facebookSignUp(navController, callbackManager, context) },
            modifier = Modifier
                .width(170.dp)
                .height(45.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1877F2)) // Facebook blue color
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

fun signUpWithGoogle(googleSignInClient: GoogleSignInClient, context: Context) {
    val signInIntent = googleSignInClient.signInIntent
    (context as Activity).startActivityForResult(signInIntent, 102)
}
fun handleGoogleSignInSuccess(account: GoogleSignInAccount, navController: NavController, context: Context) {
    Toast.makeText(context, "Bienvenue ${account.displayName}", Toast.LENGTH_SHORT).show()
    // Naviguer vers la page d'accueil
    navController.navigate("home") // Remplacez "home" par le bon écran
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
